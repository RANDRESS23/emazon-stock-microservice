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
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    void testGetAllProductsAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "name";
        boolean ascending = true;

        // Simular datos de Product, Category y Brand
        Brand brand = new Brand(1L, "Brand A", "Description A");
        Category category = new Category(1L, "Category A", "Description A");
        Product product = new Product(1L, "Product A", "Description A", 10L, BigDecimal.valueOf(100), List.of(category), brand);

        // Simular ProductResponse
        BrandDto brandDto = new BrandDto(1L, "Brand A");
        CategoryDto categoryDto = new CategoryDto(1L, "Category A");
        ProductResponse productResponse = new ProductResponse(1L, "Product A", "Description A", 10L, BigDecimal.valueOf(100), List.of(categoryDto), brandDto);

        // Simular página de Product y ProductResponse
        CustomPage<Product> pageOfProducts = new CustomPage<>();
        pageOfProducts.setPageNumber(page);
        pageOfProducts.setPageSize(size);
        pageOfProducts.setTotalElements(1);
        pageOfProducts.setTotalPages(1);
        pageOfProducts.setContent(List.of(product));

        CustomPage<ProductResponse> pageOfProductResponses = new CustomPage<>();
        pageOfProductResponses.setPageNumber(page);
        pageOfProductResponses.setPageSize(size);
        pageOfProductResponses.setTotalElements(1);
        pageOfProductResponses.setTotalPages(1);
        pageOfProductResponses.setContent(List.of(productResponse));

        // Mockear comportamiento del service y mapper
        when(productServicePort.getAllProducts(page, size, ascending, sortBy)).thenReturn(pageOfProducts);
        when(productResponseMapper.toPageOfProductResponse(pageOfProducts)).thenReturn(pageOfProductResponses);

        // Act
        ResponseEntity<CustomPage<ProductResponse>> response = productRestController.getAllProducts(page, size, "asc", sortBy);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(page, response.getBody().getPageNumber());
        assertEquals(size, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getContent().size());

        ProductResponse responseProduct = response.getBody().getContent().get(0);
        assertEquals(productResponse.getProductId(), responseProduct.getProductId());
        assertEquals(productResponse.getName(), responseProduct.getName());
        assertEquals(productResponse.getDescription(), responseProduct.getDescription());
        assertEquals(productResponse.getPrice(), responseProduct.getPrice());
        assertEquals(productResponse.getQuantity(), responseProduct.getQuantity());

        // Verificar categorías y marca en la respuesta
        assertEquals(1, responseProduct.getCategories().size());
        assertEquals(categoryDto.getCategoryId(), responseProduct.getCategories().get(0).getCategoryId());
        assertEquals(categoryDto.getName(), responseProduct.getCategories().get(0).getName());

        assertEquals(brandDto.getBrandId(), responseProduct.getBrand().getBrandId());
        assertEquals(brandDto.getName(), responseProduct.getBrand().getName());

        // Verificar que el servicio y mapper fueron llamados
        verify(productServicePort).getAllProducts(page, size, ascending, sortBy);
        verify(productResponseMapper).toPageOfProductResponse(pageOfProducts);
    }

    @Test
    void testGetAllProductsDescending() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "name";
        boolean ascending = false;

        // Simular datos de Product, Category y Brand
        Brand brand = new Brand(2L, "Brand B", "Description A");
        Category category = new Category(2L, "Category B", "Description A");
        Product product = new Product(2L, "Product B", "Description B", 20L, BigDecimal.valueOf(200), List.of(category), brand);

        // Simular ProductResponse
        BrandDto brandDto = new BrandDto(2L, "Brand B");
        CategoryDto categoryDto = new CategoryDto(2L, "Category B");
        ProductResponse productResponse = new ProductResponse(2L, "Product B", "Description B", 20L, BigDecimal.valueOf(200), List.of(categoryDto), brandDto);

        // Simular página de Product y ProductResponse
        CustomPage<Product> pageOfProducts = new CustomPage<>();
        pageOfProducts.setPageNumber(page);
        pageOfProducts.setPageSize(size);
        pageOfProducts.setTotalElements(1);
        pageOfProducts.setTotalPages(1);
        pageOfProducts.setContent(List.of(product));

        CustomPage<ProductResponse> pageOfProductResponses = new CustomPage<>();
        pageOfProductResponses.setPageNumber(page);
        pageOfProductResponses.setPageSize(size);
        pageOfProductResponses.setTotalElements(1);
        pageOfProductResponses.setTotalPages(1);
        pageOfProductResponses.setContent(List.of(productResponse));

        // Mockear comportamiento del service y mapper
        when(productServicePort.getAllProducts(page, size, ascending, sortBy)).thenReturn(pageOfProducts);
        when(productResponseMapper.toPageOfProductResponse(pageOfProducts)).thenReturn(pageOfProductResponses);

        // Act
        ResponseEntity<CustomPage<ProductResponse>> response = productRestController.getAllProducts(page, size, "desc", sortBy);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(page, response.getBody().getPageNumber());
        assertEquals(size, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getContent().size());

        ProductResponse responseProduct = response.getBody().getContent().get(0);
        assertEquals(productResponse.getProductId(), responseProduct.getProductId());
        assertEquals(productResponse.getName(), responseProduct.getName());
        assertEquals(productResponse.getDescription(), responseProduct.getDescription());
        assertEquals(productResponse.getPrice(), responseProduct.getPrice());
        assertEquals(productResponse.getQuantity(), responseProduct.getQuantity());

        // Verificar categorías y marca en la respuesta
        assertEquals(1, responseProduct.getCategories().size());
        assertEquals(categoryDto.getCategoryId(), responseProduct.getCategories().get(0).getCategoryId());
        assertEquals(categoryDto.getName(), responseProduct.getCategories().get(0).getName());

        assertEquals(brandDto.getBrandId(), responseProduct.getBrand().getBrandId());
        assertEquals(brandDto.getName(), responseProduct.getBrand().getName());

        // Verificar que el servicio y mapper fueron llamados
        verify(productServicePort).getAllProducts(page, size, ascending, sortBy);
        verify(productResponseMapper).toPageOfProductResponse(pageOfProducts);
    }
}