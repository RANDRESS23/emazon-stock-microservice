package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.microservicio_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}