package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.CategoryValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {
    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private CategoryValidation categoryValidation;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void testCreateCategorySuccessfully() {
        // Arrange
        Category category = new Category(null, "Electrónica", "Categoría para electrónicos");

        when(categoryPersistencePort.getCategory(category.getName())).thenReturn(Optional.empty());

        // Act
        categoryUseCase.saveCategory(category);

        // Assert
        verify(categoryPersistencePort).saveCategory(category);
    }

    @Test
    void testCreateCategoryWithExistingName() {
        // Arrange
        Category category = new Category(null, "Electrónica", "Categoría para electrónicos");

        when(categoryPersistencePort.getCategory(category.getName())).thenReturn(Optional.of(category));

        // Act & Assert
        AlreadyExistsFieldException exception = assertThrows(
                AlreadyExistsFieldException.class,
                () -> categoryUseCase.saveCategory(category)
        );

        assertEquals(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE, exception.getMessage());
        verify(categoryPersistencePort, never()).saveCategory(category);
    }

    @Test
    void testDeleteCategorySuccessfully() {
        // Arrange
        String categoryName = "Electrónica";
        Category category = new Category(null, categoryName, "Categoría para electrónicos");

        when(categoryPersistencePort.getCategory(categoryName)).thenReturn(Optional.of(category));

        // Act
        categoryUseCase.deleteCategory(categoryName);

        // Assert
        verify(categoryPersistencePort).deleteCategory(categoryName);
    }

    @Test
    void testDeleteCategoryNotFound() {
        // Arrange
        String categoryName = "Electrónica";

        when(categoryPersistencePort.getCategory(categoryName)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> categoryUseCase.deleteCategory(categoryName)
        );

        assertEquals(DomainConstants.CATEGORY_NOT_FOUND, exception.getMessage());
        verify(categoryPersistencePort, never()).deleteCategory(categoryName);
    }

    @Test
    void testGetAllCategoriesSuccessfullyAscending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = true;

        Category category1 = new Category(null, "A", "Description A");
        Category category2 = new Category(null, "B", "Description B");

        Page<Category> categoryPage = new PageImpl<>(
                Arrays.asList(category1, category2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                2
        );

        when(categoryPersistencePort.getAllCategories(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("A", result.getContent().get(0).getName());
        assertEquals("B", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllCategoriesSuccessfullyDescending() {
        // Arrange
        int page = 0;
        int size = 5;
        boolean ascending = false;

        Category category1 = new Category(null, "B", "Description B");
        Category category2 = new Category(null, "A", "Description A");

        Page<Category> categoryPage = new PageImpl<>(
                Arrays.asList(category1, category2),
                PageRequest.of(page, size, Sort.by("name").descending()),
                2
        );

        when(categoryPersistencePort.getAllCategories(PageRequest.of(page, size, Sort.by("name").descending()))).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("B", result.getContent().get(0).getName());
        assertEquals("A", result.getContent().get(1).getName());
    }

    @Test
    void testGetAllCategoriesPagination() {
        // Arrange
        int page = 1;
        int size = 5;
        boolean ascending = true;

        Category category1 = new Category(null, "C", "Description C");
        Category category2 = new Category(null, "D", "Description D");

        Page<Category> categoryPage = new PageImpl<>(
                Arrays.asList(category1, category2),
                PageRequest.of(page, size, Sort.by("name").ascending()),
                10
        );

        when(categoryPersistencePort.getAllCategories(PageRequest.of(page, size, Sort.by("name").ascending()))).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(10, result.getTotalElements());
        assertEquals(2, result.getNumberOfElements());
        assertEquals(category1, result.getContent().get(0));
        assertEquals(category2, result.getContent().get(1));
    }
}