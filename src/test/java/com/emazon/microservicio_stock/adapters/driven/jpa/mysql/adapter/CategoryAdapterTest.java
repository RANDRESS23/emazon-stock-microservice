package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryAdapterTest {
    @Mock
    private ICategoryRepository categoryRepository;

    @Mock
    private ICategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryAdapter categoryAdapter;

    private CategoryEntity categoryEntity1;
    private CategoryEntity categoryEntity2;
    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        categoryEntity1 = new CategoryEntity();
        categoryEntity1.setCategoryId(1L);
        categoryEntity1.setName("Category 1");
        categoryEntity1.setDescription("Description 1");

        categoryEntity2 = new CategoryEntity();
        categoryEntity2.setCategoryId(2L);
        categoryEntity2.setName("Category 2");
        categoryEntity2.setDescription("Description 2");

        category1 = new Category(1L, "Category 1", "Description 1");
        category2 = new Category(2L, "Category 2", "Description 2");
    }

    @Test
    void saveCategory() {
        Category category = new Category(1L, "Electronics", "Electronics");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronics");

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryEntityMapper.toEntity(category)).thenReturn(categoryEntity);
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(categoryEntity);

        // Verificamos que no se lanza ninguna excepción al guardar la categoría
        assertDoesNotThrow(() -> categoryAdapter.saveCategory(category));

        // Verificamos que el método save fue llamado una vez
        verify(categoryRepository, times(1)).save(categoryEntity);
    }

    @Test
    void saveCategoryWhenAlreadyExists() {
        Category category = new Category(1L, "Electronics", "Electronics");
        CategoryEntity categoryEntity = new CategoryEntity(1L, "Electronics", "Electronics");

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(categoryEntity));

        // Verificamos que se lanza una excepción cuando la categoría ya existe
        assertThrows(AlreadyExistsException.class, () -> categoryAdapter.saveCategory(category));

        // Verificamos que el método save nunca fue llamado
        verify(categoryRepository, never()).save(any(CategoryEntity.class));
    }

    @Test
    void testGetAllCategoriesAscending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DomainConstants.FIELD_NAME).ascending());

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> pageCategoryEntities = new PageImpl<>(categoryEntities, pageable, categoryEntities.size());

        when(categoryRepository.findAll(pageable)).thenReturn(pageCategoryEntities);
        when(categoryEntityMapper.toPageOfCategories(pageCategoryEntities)).thenReturn(new PageImpl<>(List.of(category1, category2), pageable, categoryEntities.size()));

        // Act
        CustomPage<Category> result = categoryAdapter.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Category firstCategory = result.getContent().get(0);
        assertEquals(1L, firstCategory.getCategoryId());
        assertEquals("Category 1", firstCategory.getName());

        Category secondCategory = result.getContent().get(1);
        assertEquals(2L, secondCategory.getCategoryId());
        assertEquals("Category 2", secondCategory.getName());

        verify(categoryRepository).findAll(pageable);
        verify(categoryEntityMapper).toPageOfCategories(pageCategoryEntities);
    }

    @Test
    void testGetAllCategoriesDescending() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = false;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DomainConstants.FIELD_NAME).descending());

        List<CategoryEntity> categoryEntities = List.of(categoryEntity1, categoryEntity2);
        Page<CategoryEntity> pageCategoryEntities = new PageImpl<>(categoryEntities, pageable, categoryEntities.size());

        when(categoryRepository.findAll(pageable)).thenReturn(pageCategoryEntities);
        when(categoryEntityMapper.toPageOfCategories(pageCategoryEntities)).thenReturn(new PageImpl<>(List.of(category1, category2), pageable, categoryEntities.size()));

        // Act
        CustomPage<Category> result = categoryAdapter.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2, result.getContent().size());

        Category firstCategory = result.getContent().get(0);
        assertEquals(1L, firstCategory.getCategoryId());
        assertEquals("Category 1", firstCategory.getName());

        Category secondCategory = result.getContent().get(1);
        assertEquals(2L, secondCategory.getCategoryId());
        assertEquals("Category 2", secondCategory.getName());

        verify(categoryRepository).findAll(pageable);
        verify(categoryEntityMapper).toPageOfCategories(pageCategoryEntities);
    }

    @Test
    void testGetAllCategoriesWithEmptyPage() {
        // Arrange
        int page = 0;
        int size = 10;
        boolean ascending = true;
        Pageable pageable = PageRequest.of(page, size, Sort.by(DomainConstants.FIELD_NAME).ascending());

        Page<CategoryEntity> pageCategoryEntities = new PageImpl<>(List.of(), pageable, 0);

        when(categoryRepository.findAll(pageable)).thenReturn(pageCategoryEntities);
        when(categoryEntityMapper.toPageOfCategories(pageCategoryEntities)).thenReturn(new PageImpl<>(List.of(), pageable, 0));

        // Act
        CustomPage<Category> result = categoryAdapter.getAllCategories(page, size, ascending);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getTotalPages());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertTrue(result.getContent().isEmpty());

        verify(categoryRepository).findAll(pageable);
        verify(categoryEntityMapper).toPageOfCategories(pageCategoryEntities);
    }
}