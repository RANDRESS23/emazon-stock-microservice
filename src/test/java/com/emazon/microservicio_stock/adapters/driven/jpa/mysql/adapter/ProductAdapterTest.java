package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util.DrivenConstants;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class ProductAdapterTest {
    @Mock
    private IProductRepository productRepository;

    @Mock
    private IProductEntityMapper productEntityMapper;

    @InjectMocks
    private ProductAdapter productAdapter;

    private ProductEntity productEntity1;
    private ProductEntity productEntity2;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        Category category = new Category(1L, "Electronics", "Category for electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");

        productEntity1 = new ProductEntity();
        productEntity1.setProductId(1L);
        productEntity1.setName("Product 1");
        productEntity1.setDescription("Description 1");
        productEntity1.setQuantity(10L);
        productEntity1.setPrice(BigDecimal.valueOf(100.0));

        productEntity2 = new ProductEntity();
        productEntity2.setProductId(2L);
        productEntity2.setName("Product 2");
        productEntity2.setDescription("Description 2");
        productEntity2.setQuantity(20L);
        productEntity2.setPrice(BigDecimal.valueOf(200.0));

        product1 = new Product(1L, "Product 1", "Description 1", 10L, BigDecimal.valueOf(100.0), List.of(category), brand);
        product2 = new Product(2L, "Product 2", "Description 2", 20L, BigDecimal.valueOf(200.0), List.of(category), brand);
    }

    @Test
    void saveProduct() {
        when(productRepository.findByName(product1.getName())).thenReturn(Optional.empty());
        when(productEntityMapper.toEntity(product1)).thenReturn(productEntity1);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity1);

        // Verificamos que no se lanza ninguna excepción al guardar el producto
        assertDoesNotThrow(() -> productAdapter.saveProduct(product1));

        // Verificamos que el método save fue llamado una vez
        verify(productRepository, times(1)).save(productEntity1);
    }

    @Test
    void saveProductWhenAlreadyExists() {
        when(productRepository.findByName(product1.getName())).thenReturn(Optional.of(productEntity1));

        // Verificamos que se lanza una excepción cuando el producto ya existe
        assertThrows(AlreadyExistsException.class, () -> productAdapter.saveProduct(product1));

        // Verificamos que el método save nunca fue llamado
        verify(productRepository, never()).save(any(ProductEntity.class));
    }

    @Test
    void testGetAllProductsSortByProductNameAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        String sortBy = DrivenConstants.SORT_BY_PRODUCT_NAME;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DrivenConstants.SORT_BY_PRODUCT_NAME).ascending());

        List<ProductEntity> productEntities = List.of(productEntity1, productEntity2);
        Page<ProductEntity> pageProductEntities = new PageImpl<>(productEntities, pageable, productEntities.size());

        when(productRepository.findAll(pageable)).thenReturn(pageProductEntities);
        when(productEntityMapper.toPageOfProducts(pageProductEntities)).thenReturn(new PageImpl<>(List.of(product1, product2), pageable, productEntities.size()));

        // Act
        CustomPage<Product> result = productAdapter.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Product firstProduct = result.getContent().get(0);
        assertEquals(1L, firstProduct.getProductId());
        assertEquals("Product 1", firstProduct.getName());

        Product secondProduct = result.getContent().get(1);
        assertEquals(2L, secondProduct.getProductId());
        assertEquals("Product 2", secondProduct.getName());

        verify(productRepository).findAll(pageable);
        verify(productEntityMapper).toPageOfProducts(pageProductEntities);
    }

    @Test
    void testGetAllProductsSortByBrandDescending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = false;
        String sortBy = DrivenConstants.FIELD_BRAND;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DrivenConstants.SORT_BY_BRAND_NAME).descending());

        List<ProductEntity> productEntities = List.of(productEntity1, productEntity2);
        Page<ProductEntity> pageProductEntities = new PageImpl<>(productEntities, pageable, productEntities.size());

        when(productRepository.findAll(pageable)).thenReturn(pageProductEntities);
        when(productEntityMapper.toPageOfProducts(pageProductEntities)).thenReturn(new PageImpl<>(List.of(product1, product2), pageable, productEntities.size()));

        // Act
        CustomPage<Product> result = productAdapter.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Product firstProduct = result.getContent().get(0);
        assertEquals(1L, firstProduct.getProductId());
        assertEquals("Product 1", firstProduct.getName());

        Product secondProduct = result.getContent().get(1);
        assertEquals(2L, secondProduct.getProductId());
        assertEquals("Product 2", secondProduct.getName());

        verify(productRepository).findAll(pageable);
        verify(productEntityMapper).toPageOfProducts(pageProductEntities);
    }

    @Test
    void testGetAllProductsSortByCategoryAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        String sortBy = DrivenConstants.FIELD_CATEGORIES;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DrivenConstants.SORT_BY_CATEGORY_NAME).ascending());

        List<ProductEntity> productEntities = List.of(productEntity1, productEntity2);
        Page<ProductEntity> pageProductEntities = new PageImpl<>(productEntities, pageable, productEntities.size());

        when(productRepository.findAll(pageable)).thenReturn(pageProductEntities);
        when(productEntityMapper.toPageOfProducts(pageProductEntities)).thenReturn(new PageImpl<>(List.of(product1, product2), pageable, productEntities.size()));

        // Act
        CustomPage<Product> result = productAdapter.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Product firstProduct = result.getContent().get(0);
        assertEquals(1L, firstProduct.getProductId());
        assertEquals("Product 1", firstProduct.getName());

        Product secondProduct = result.getContent().get(1);
        assertEquals(2L, secondProduct.getProductId());
        assertEquals("Product 2", secondProduct.getName());

        verify(productRepository).findAll(pageable);
        verify(productEntityMapper).toPageOfProducts(pageProductEntities);
    }

    @Test
    void testGetAllProductsWithEmptyPage() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        String sortBy = DrivenConstants.SORT_BY_PRODUCT_NAME;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DrivenConstants.SORT_BY_PRODUCT_NAME).ascending());

        Page<ProductEntity> pageProductEntities = new PageImpl<>(List.of(), pageable, 0);

        when(productRepository.findAll(pageable)).thenReturn(pageProductEntities);
        when(productEntityMapper.toPageOfProducts(pageProductEntities)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

        // Act
        CustomPage<Product> result = productAdapter.getAllProducts(page, size, ascending, sortBy);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertTrue(result.getContent().isEmpty());

        verify(productRepository).findAll(pageable);
        verify(productEntityMapper).toPageOfProducts(pageProductEntities);
    }
}