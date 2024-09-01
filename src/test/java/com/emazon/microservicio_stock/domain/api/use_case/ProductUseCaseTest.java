package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.DuplicateCategoryException;
import com.emazon.microservicio_stock.domain.exception.InvalidProductNameException;
import com.emazon.microservicio_stock.domain.exception.ProductNotFoundException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
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

    @InjectMocks
    private ProductUseCase productUseCase;

    @Test
    void testSaveProductSuccessfully() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);

        when(productPersistencePort.getProductByName(product.getName())).thenReturn(Optional.empty());
        when(brandPersistencePort.getBrandById(brand.getIdBrand())).thenReturn(Optional.of(brand));
        when(categoryPersistencePort.getCategoryById(category.getIdCategory())).thenReturn(Optional.of(category));

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
        InvalidProductNameException exception = assertThrows(
                InvalidProductNameException.class,
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
        when(categoryPersistencePort.getCategoryById(category.getIdCategory())).thenReturn(Optional.of(category));
        when(brandPersistencePort.getBrandById(brand.getIdBrand())).thenReturn(Optional.of(brand));

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
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
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
        ProductNotFoundException exception = assertThrows(
                ProductNotFoundException.class,
                () -> productUseCase.getProduct(productName)
        );

        assertEquals(DomainConstants.PRODUCT_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testGetAllProductsSuccessfully() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = true;
        String sortBy = DomainConstants.FIELD_NAME;

        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        Product product1 = new Product(1L, "A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        Product product2 = new Product(2L, "B", "Description B", 10L, new BigDecimal(100), List.of(category), brand);

        Page<Product> productPage = new PageImpl<>(
                Arrays.asList(product1, product2),
                PageRequest.of(page, size, Sort.by(sortBy).ascending()),
                2
        );

        when(productPersistencePort.getAllProducts(PageRequest.of(page, size, Sort.by(DomainConstants.SORT_BY_PRODUCT_NAME).ascending())))
                .thenReturn(productPage);

        // Act
        Page<Product> result = productUseCase.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("A", result.getContent().get(0).getName());
        assertEquals("B", result.getContent().get(1).getName());
    }
}