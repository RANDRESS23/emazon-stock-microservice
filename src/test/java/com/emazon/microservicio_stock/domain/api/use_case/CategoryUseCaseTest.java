package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.CategoryValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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
    void testGetAllCategories() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;

        // Crear categorías simuladas
        Category category1 = new Category(1L, "Category 1", "Description 1");
        Category category2 = new Category(2L, "Category 2", "Description 2");

        // Simular CustomPage de categorías
        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setPageNumber(page);
        customPage.setPageSize(size);
        customPage.setTotalElements(2);
        customPage.setTotalPages(1);
        customPage.setContent(List.of(category1, category2));

        // Mockear el comportamiento del persistence port
        when(categoryPersistencePort.getAllCategories(page, size, ascending)).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Category resultCategory1 = result.getContent().get(0);
        Category resultCategory2 = result.getContent().get(1);

        assertEquals(category1.getCategoryId(), resultCategory1.getCategoryId());
        assertEquals(category1.getName(), resultCategory1.getName());
        assertEquals(category2.getCategoryId(), resultCategory2.getCategoryId());
        assertEquals(category2.getName(), resultCategory2.getName());

        // Verificar que el persistence port fue llamado con los parámetros correctos
        verify(categoryPersistencePort).getAllCategories(page, size, ascending);
    }

    @Test
    void testGetAllCategoriesDescending() {
        // Arrange
        int page = 1;
        int size = 5;
        boolean ascending = false;

        // Crear categorías simuladas
        Category category1 = new Category(3L, "Category 3", "Description 1");
        Category category2 = new Category(4L, "Category 4", "Description 2");

        // Simular CustomPage de categorías
        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setPageNumber(page);
        customPage.setPageSize(size);
        customPage.setTotalElements(2);
        customPage.setTotalPages(1);
        customPage.setContent(List.of(category1, category2));

        // Mockear el comportamiento del persistence port
        when(categoryPersistencePort.getAllCategories(page, size, ascending)).thenReturn(customPage);

        // Act
        CustomPage<Category> result = categoryUseCase.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Category resultCategory1 = result.getContent().get(0);
        Category resultCategory2 = result.getContent().get(1);

        assertEquals(category1.getCategoryId(), resultCategory1.getCategoryId());
        assertEquals(category1.getName(), resultCategory1.getName());
        assertEquals(category2.getCategoryId(), resultCategory2.getCategoryId());
        assertEquals(category2.getName(), resultCategory2.getName());

        // Verificar que el persistence port fue llamado con los parámetros correctos
        verify(categoryPersistencePort).getAllCategories(page, size, ascending);
    }
}