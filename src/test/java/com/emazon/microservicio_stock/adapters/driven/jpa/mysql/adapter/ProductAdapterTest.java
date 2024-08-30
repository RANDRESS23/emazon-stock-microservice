package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.ProductEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.ProductAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.IProductEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.IProductRepository;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Test
    void saveProduct() {
        Category category = new Category(1L, "Electronics", "Electronics");
        Brand brand = new Brand(1L, "Electronics", "Electronics");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronics");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronics");
        List<CategoryEntity> categoriesEntity = new ArrayList<>();
        categoriesEntity.add(categoryEntity);

        Product product = new Product(1L, "Electronics", "Electronics", 10L, new BigDecimal(25.5), categories, brand);
        ProductEntity productEntity = new ProductEntity(1L, "Electronics", "Electronics", 10L, new BigDecimal(25.5), categoriesEntity, brandEntity);

        when(productRepository.findByName(product.getName())).thenReturn(Optional.empty());
        when(productEntityMapper.toEntity(product)).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        // Verificamos que no se lanza ninguna excepción al guardar el producto
        assertDoesNotThrow(() -> productAdapter.saveProduct(product));

        // Verificamos que el método save fue llamado una vez
        verify(productRepository, times(1)).save(productEntity);
    }

    @Test
    void saveProductWhenAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Electronics");
        Brand brand = new Brand(1L, "Electronics", "Electronics");
        List<Category> categories = new ArrayList<>();
        categories.add(category);

        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronics");
        BrandEntity brandEntity = new BrandEntity(1L, "Electronics", "Electronics");
        List<CategoryEntity> categoriesEntity = new ArrayList<>();
        categoriesEntity.add(categoryEntity);

        Product product = new Product(1L, "Electronics", "Electronics", 10L, new BigDecimal(25.5), categories, brand);
        ProductEntity productEntity = new ProductEntity(1L, "Electronics", "Electronics", 10L, new BigDecimal(25.5), categoriesEntity, brandEntity);

        when(productRepository.findByName(product.getName())).thenReturn(Optional.of(productEntity));

        // Verificamos que se lanza una excepción cuando el producto ya existe
        assertThrows(ProductAlreadyExistsException.class, () -> productAdapter.saveProduct(product));

        // Verificamos que el método save nunca fue llamado
        verify(productRepository, never()).save(any(ProductEntity.class));
    }
}