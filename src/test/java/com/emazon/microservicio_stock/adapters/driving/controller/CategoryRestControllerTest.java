package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddCategoryRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryResponseMapper;
import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerTest {
    @Mock
    private ICategoryServicePort categoryServicePort;

    @Mock
    private ICategoryRequestMapper categoryRequestMapper;

    @Mock
    private ICategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryRestController categoryRestController;

    @Test
    void addCategory_shouldReturnCreatedCategory() {
        // Arrange
        AddCategoryRequest request = new AddCategoryRequest("Electronics", "Category for electronics");
        Category category = new Category(null, "Electronics", "Category for electronics");
        Category categorySaved = new Category(1L, "Electronics", "Category for electronics");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Electronics", "Category for electronics");

        when(categoryRequestMapper.addRequestToCategory(request)).thenReturn(category);
        when(categoryServicePort.saveCategory(category)).thenReturn(categorySaved);
        when(categoryResponseMapper.toCategoryResponse(categorySaved)).thenReturn(categoryResponse);

        // Act
        ResponseEntity<CategoryResponse> response = categoryRestController.addCategory(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryResponse, response.getBody());
        verify(categoryRequestMapper, times(1)).addRequestToCategory(request);
        verify(categoryServicePort, times(1)).saveCategory(category);
        verify(categoryResponseMapper, times(1)).toCategoryResponse(categorySaved);
    }

    @Test
    void getCategory_shouldReturnCategoryResponse() {
        // Arrange
        String name = "Electronics";
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Electronics", "Category for electronics");

        when(categoryServicePort.getCategory(name)).thenReturn(category);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        ResponseEntity<CategoryResponse> response = categoryRestController.getCategory(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryResponse, response.getBody());
        verify(categoryServicePort, times(1)).getCategory(name);
        verify(categoryResponseMapper, times(1)).toCategoryResponse(category);
    }

    @Test
    void getAllCategories_shouldReturnPageOfCategoryResponses() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortOrder = "asc";
        Category category = new Category(1L, "Electronics", "Category for electronics");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Electronics", "Category for electronics");

        Page<Category> categoryPage = new PageImpl<>(List.of(category));
        Page<CategoryResponse> responsePage = new PageImpl<>(List.of(categoryResponse));

        when(categoryServicePort.getAllCategories(page, size, true)).thenReturn(categoryPage);
        when(categoryResponseMapper.toCategoryResponse(category)).thenReturn(categoryResponse);

        // Act
        ResponseEntity<Page<CategoryResponse>> response = categoryRestController.getAllCategories(page, size, sortOrder);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responsePage, response.getBody());
        verify(categoryServicePort, times(1)).getAllCategories(page, size, true);
        verify(categoryResponseMapper, times(1)).toCategoryResponse(category);
    }

    @Test
    void deleteCategory_shouldReturnNoContent() {
        // Arrange
        String name = "Electronics";

        // Act
        ResponseEntity<Void> response = categoryRestController.deleteCategory(name);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryServicePort, times(1)).deleteCategory(name);
    }
}