package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddProductRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.request.UpdateProductQuantityRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.BrandDto;
import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryDto;
import com.emazon.microservicio_stock.adapters.driving.dto.response.ProductResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.IProductResponseMapper;
import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductRestControllerTest {
    @Mock
    private IProductServicePort productServicePort;

    @Mock
    private IProductRequestMapper productRequestMapper;

    @Mock
    private IProductResponseMapper productResponseMapper;

    @InjectMocks
    private ProductRestController productRestController;

    @Test
    void addProduct_shouldReturnCreatedProduct() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        BrandDto brandDto = new BrandDto(1L, "Brand A");
        AddProductRequest request = new AddProductRequest("Product A", "Description A", 10L, new BigDecimal(100), List.of(1L), 1L);
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        Product savedProduct = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        ProductResponse productResponse = new ProductResponse(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(categoryDto), brandDto);

        when(productRequestMapper.addRequestToProduct(request)).thenReturn(product);
        when(productServicePort.saveProduct(product)).thenReturn(savedProduct);
        when(productResponseMapper.toProductResponse(savedProduct)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponse> response = productRestController.addProduct(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productRequestMapper, times(1)).addRequestToProduct(request);
        verify(productServicePort, times(1)).saveProduct(product);
        verify(productResponseMapper, times(1)).toProductResponse(savedProduct);
    }

    @Test
    void getProduct_shouldReturnProductResponse() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        BrandDto brandDto = new BrandDto(1L, "Brand A");
        String productName = "TestProduct";
        Product product = new Product(null, productName, "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        ProductResponse productResponse = new ProductResponse(null, productName, "Description A", 10L, new BigDecimal(100), List.of(categoryDto), brandDto);

        when(productServicePort.getProduct(productName)).thenReturn(product);
        when(productResponseMapper.toProductResponse(product)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponse> response = productRestController.getProduct(productName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productServicePort, times(1)).getProduct(productName);
        verify(productResponseMapper, times(1)).toProductResponse(product);
    }

    @Test
    void getAllProducts_shouldReturnPageOfProductResponses() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "asc";
        String sortBy = "name";
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        BrandDto brandDto = new BrandDto(1L, "Brand A");
        Product product = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        ProductResponse productResponse = new ProductResponse(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(categoryDto), brandDto);

        Page<Product> productPage = new PageImpl<>(List.of(product));
        Page<ProductResponse> responsePage = new PageImpl<>(List.of(productResponse));

        when(productServicePort.getAllProducts(page, size, true, sortBy)).thenReturn(productPage);
        when(productResponseMapper.toProductResponse(product)).thenReturn(productResponse);

        // Act
        ResponseEntity<Page<ProductResponse>> response = productRestController.getAllProducts(page, size, sortOrder, sortBy);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsePage, response.getBody());
        verify(productServicePort, times(1)).getAllProducts(page, size, true, sortBy);
        verify(productResponseMapper, times(1)).toProductResponse(product);
    }

    @Test
    void updateProductQuantity_shouldReturnUpdatedProduct() {
        // Arrange
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryDto categoryDto = new CategoryDto(1L, "Electronics");
        Brand brand = new Brand(1L, "Brand A", "Brand Description");
        BrandDto brandDto = new BrandDto(1L, "Brand A");
        UpdateProductQuantityRequest request = new UpdateProductQuantityRequest(1L, 10L);
        Product updatedProduct = new Product(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(category), brand);
        ProductResponse productResponse = new ProductResponse(null, "Product A", "Description A", 10L, new BigDecimal(100), List.of(categoryDto), brandDto);

        when(productServicePort.updateProductQuantity(request.getProductId(), request.getExtraQuantity())).thenReturn(updatedProduct);
        when(productResponseMapper.toProductResponse(updatedProduct)).thenReturn(productResponse);

        // Act
        ResponseEntity<ProductResponse> response = productRestController.updateProductQuantity(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
        verify(productServicePort, times(1)).updateProductQuantity(request.getProductId(), request.getExtraQuantity());
        verify(productResponseMapper, times(1)).toProductResponse(updatedProduct);
    }

    @Test
    void deleteProduct_shouldReturnNoContent() {
        // Arrange
        String productName = "TestProduct";

        // Act
        ResponseEntity<Void> response = productRestController.deleteProduct(productName);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productServicePort, times(1)).deleteProduct(productName);
    }
}