package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
    void deleteCategory(String name);
    Optional<Category> getCategory(String name);
    Optional<Category> getCategoryById(Long idCategory);
    Page<Category> getAllCategories(Pageable pageable);
}
