package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.DuplicateCategoryException;
import com.emazon.microservicio_stock.domain.exception.InvalidSortByParamException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.ProductValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductUseCaseTest {
    @Mock
    private IProductPersistencePort productPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @Mock
    private ProductValidation productValidation;

    @InjectMocks
    private ProductUseCase productUseCase;

    private CustomPage<Product> createSampleProductPage(int page, int size) {
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        Product product1 = new Product(1L, "Product 1", "Description 1", 10L, new BigDecimal("100.00"), List.of(), brand);
        Product product2 = new Product(2L, "Product 2", "Description 2", 5L, new BigDecimal("50.00"), List.of(), brand);

        CustomPage<Product> customPage = new CustomPage<>();
        customPage.setPageNumber(page);
        customPage.setPageSize(size);
        customPage.setTotalElements(2);
        customPage.setTotalPages(1);
        customPage.setContent(List.of(product1, product2));

        return customPage;
    }

    @Test
    void testSaveProductSuccessfully() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);

        when(productPersistencePort.getProductByName(product.getName())).thenReturn(Optional.empty());
        when(brandPersistencePort.getBrandById(brand.getBrandId())).thenReturn(Optional.of(brand));
        when(categoryPersistencePort.getCategoryById(category.getCategoryId())).thenReturn(Optional.of(category));

        // Act
        productUseCase.saveProduct(product);

        // Assert
        verify(productPersistencePort).saveProduct(product);
    }

    @Test
    void testSaveProductWithExistingName() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);

        when(productPersistencePort.getProductByName(product.getName())).thenReturn(Optional.of(product));

        // Act & Assert
        AlreadyExistsFieldException exception = assertThrows(
                AlreadyExistsFieldException.class,
                () -> productUseCase.saveProduct(product)
        );

        assertEquals(DomainConstants.PRODUCT_ALREADY_EXISTS_MESSAGE, exception.getMessage());
        verify(productPersistencePort, never()).saveProduct(product);
    }

    @Test
    void testSaveProductWithDuplicateCategory() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        // La lista de categorías contiene categorías duplicadas
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category, category), brand);

        // Simulamos que no existe un producto con el mismo nombre
        when(productPersistencePort.getProductByName(product.getName())).thenReturn(Optional.empty());
        // Simulamos que la categoría y la marca existen
        when(categoryPersistencePort.getCategoryById(category.getCategoryId())).thenReturn(Optional.of(category));
        when(brandPersistencePort.getBrandById(brand.getBrandId())).thenReturn(Optional.of(brand));

        // Act & Assert
        DuplicateCategoryException exception = assertThrows(
                DuplicateCategoryException.class,
                () -> productUseCase.saveProduct(product)
        );

        // Verificamos que el mensaje de la excepción sea el esperado
        assertEquals(DomainConstants.DUPLICATE_CATEGORY_MESSAGE, exception.getMessage());

        // Verificamos que el producto no se haya intentado guardar
        verify(productPersistencePort, never()).saveProduct(product);
    }

    @Test
    void testDeleteProductSuccessfully() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        String productName = "Product A";
        Product product = new Product(1L, productName, "Description A", 10L, new BigDecimal(100), List.of(category), brand);

        when(productPersistencePort.getProductByName(productName)).thenReturn(Optional.of(product));

        // Act
        productUseCase.deleteProduct(productName);

        // Assert
        verify(productPersistencePort).deleteProduct(productName);
    }

    @Test
    void testDeleteProductNotFound() {
        // Arrange
        String productName = "Product A";

        when(productPersistencePort.getProductByName(productName)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> productUseCase.deleteProduct(productName)
        );

        assertEquals(DomainConstants.PRODUCT_NOT_FOUND, exception.getMessage());
        verify(productPersistencePort, never()).deleteProduct(productName);
    }

    @Test
    void testGetProductSuccessfully() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        String productName = "Product A";
        Product product = new Product(1L, productName, "Description A", 10L, new BigDecimal(100), List.of(category), brand);

        when(productPersistencePort.getProductByName(productName)).thenReturn(Optional.of(product));

        // Act
        Product result = productUseCase.getProduct(productName);

        // Assert
        assertNotNull(result);
        assertEquals(productName, result.getName());
    }

    @Test
    void testGetProductNotFound() {
        // Arrange
        String productName = "Product A";

        when(productPersistencePort.getProductByName(productName)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> productUseCase.getProduct(productName)
        );

        assertEquals(DomainConstants.PRODUCT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetAllProductsWithValidSortByParam() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        String sortBy = DomainConstants.FIELD_NAME;

        CustomPage<Product> customPage = createSampleProductPage(page, size);
        when(productPersistencePort.getAllProducts(page, size, ascending, sortBy)).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Product product1 = result.getContent().get(0);
        Product product2 = result.getContent().get(1);

        assertEquals(1L, product1.getProductId());
        assertEquals("Product 1", product1.getName());
        assertEquals(2L, product2.getProductId());
        assertEquals("Product 2", product2.getName());

        verify(productPersistencePort).getAllProducts(page, size, ascending, sortBy);
    }

    @Test
    void testGetAllProductsWithInvalidSortByParamThrowsException() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        String invalidSortBy = "invalidField";

        // Act & Assert
        InvalidSortByParamException exception = assertThrows(InvalidSortByParamException.class, () ->
                productUseCase.getAllProducts(page, size, ascending, invalidSortBy));

        assertEquals(DomainConstants.INVALID_PARAM_MESSAGE, exception.getMessage());

        // Verificar que el puerto de persistencia no es llamado
        verifyNoInteractions(productPersistencePort);
    }

    @Test
    void testGetAllProductsWithAnotherValidSortByParam() {
        // Arrange
        int page = 1;
        int size = 5;
        boolean ascending = false;
        String sortBy = DomainConstants.FIELD_BRAND;

        CustomPage<Product> customPage = createSampleProductPage(page, size);
        when(productPersistencePort.getAllProducts(page, size, ascending, sortBy)).thenReturn(customPage);

        // Act
        CustomPage<Product> result = productUseCase.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Product product1 = result.getContent().get(0);
        Product product2 = result.getContent().get(1);

        assertEquals(1L, product1.getProductId());
        assertEquals("Product 1", product1.getName());
        assertEquals(2L, product2.getProductId());
        assertEquals("Product 2", product2.getName());

        verify(productPersistencePort).getAllProducts(page, size, ascending, sortBy);
    }
}