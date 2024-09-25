package com.emazon.microservicio_stock.adapters.driving.controller;

import com.emazon.microservicio_stock.adapters.driving.dto.request.AddCategoryRequest;
import com.emazon.microservicio_stock.adapters.driving.dto.response.CategoryResponse;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryRequestMapper;
import com.emazon.microservicio_stock.adapters.driving.mapper.ICategoryResponseMapper;
import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
    void deleteCategory_shouldReturnNoContent() {
        // Arrange
        String name = "Electronics";

        // Act
        ResponseEntity<Void> response = categoryRestController.deleteCategory(name);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryServicePort, times(1)).deleteCategory(name);
    }

    @Test
    void testGetAllCategoriesAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;

        // Simular Category
        Category category = new Category(1L, "Category A", "Description A");
        CategoryResponse categoryResponse = new CategoryResponse(1L, "Category A", "Description A");

        // Simular página de Category y CategoryResponse
        CustomPage<Category> pageOfCategories = new CustomPage<>();
        pageOfCategories.setPageNumber(page);
        pageOfCategories.setPageSize(size);
        pageOfCategories.setTotalElements(1);
        pageOfCategories.setTotalPages(1);
        pageOfCategories.setContent(List.of(category));

        CustomPage<CategoryResponse> pageOfCategoryResponses = new CustomPage<>();
        pageOfCategoryResponses.setPageNumber(page);
        pageOfCategoryResponses.setPageSize(size);
        pageOfCategoryResponses.setTotalElements(1);
        pageOfCategoryResponses.setTotalPages(1);
        pageOfCategoryResponses.setContent(List.of(categoryResponse));

        // Mockear el comportamiento del service y mapper
        when(categoryServicePort.getAllCategories(page, size, ascending)).thenReturn(pageOfCategories);
        when(categoryResponseMapper.toPageOfCategoryResponse(pageOfCategories)).thenReturn(pageOfCategoryResponses);

        // Act
        ResponseEntity<CustomPage<CategoryResponse>> response = categoryRestController.getAllCategories(page, size, "asc");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(page, response.getBody().getPageNumber());
        assertEquals(size, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(categoryResponse.getCategoryId(), response.getBody().getContent().get(0).getCategoryId());
        assertEquals(categoryResponse.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(categoryResponse.getDescription(), response.getBody().getContent().get(0).getDescription());

        // Verificar que el servicio y mapper fueron llamados
        verify(categoryServicePort).getAllCategories(page, size, ascending);
        verify(categoryResponseMapper).toPageOfCategoryResponse(pageOfCategories);
    }

    @Test
    void testGetAllCategoriesDescending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = false;

        // Simular Category
        Category category = new Category(2L, "Category B", "Description B");
        CategoryResponse categoryResponse = new CategoryResponse(2L, "Category B", "Description B");

        // Simular página de Category y CategoryResponse
        CustomPage<Category> pageOfCategories = new CustomPage<>();
        pageOfCategories.setPageNumber(page);
        pageOfCategories.setPageSize(size);
        pageOfCategories.setTotalElements(1);
        pageOfCategories.setTotalPages(1);
        pageOfCategories.setContent(List.of(category));

        CustomPage<CategoryResponse> pageOfCategoryResponses = new CustomPage<>();
        pageOfCategoryResponses.setPageNumber(page);
        pageOfCategoryResponses.setPageSize(size);
        pageOfCategoryResponses.setTotalElements(1);
        pageOfCategoryResponses.setTotalPages(1);
        pageOfCategoryResponses.setContent(List.of(categoryResponse));

        // Mockear el comportamiento del service y mapper
        when(categoryServicePort.getAllCategories(page, size, ascending)).thenReturn(pageOfCategories);
        when(categoryResponseMapper.toPageOfCategoryResponse(pageOfCategories)).thenReturn(pageOfCategoryResponses);

        // Act
        ResponseEntity<CustomPage<CategoryResponse>> response = categoryRestController.getAllCategories(page, size, "desc");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getTotalElements());
        assertEquals(1, response.getBody().getTotalPages());
        assertEquals(page, response.getBody().getPageNumber());
        assertEquals(size, response.getBody().getPageSize());
        assertEquals(1, response.getBody().getContent().size());
        assertEquals(categoryResponse.getCategoryId(), response.getBody().getContent().get(0).getCategoryId());
        assertEquals(categoryResponse.getName(), response.getBody().getContent().get(0).getName());
        assertEquals(categoryResponse.getDescription(), response.getBody().getContent().get(0).getDescription());

        // Verificar que el servicio y mapper fueron llamados
        verify(categoryServicePort).getAllCategories(page, size, ascending);
        verify(categoryResponseMapper).toPageOfCategoryResponse(pageOfCategories);
    }
}