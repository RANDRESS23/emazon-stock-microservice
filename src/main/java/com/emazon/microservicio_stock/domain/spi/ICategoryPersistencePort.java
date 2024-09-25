package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;

import java.util.Optional;

public interface ICategoryPersistencePort {
    Category saveCategory(Category category);
    void deleteCategory(String name);
    Optional<Category> getCategory(String name);
    Optional<Category> getCategoryById(Long idCategory);
    CustomPage<Category> getAllCategories(Integer page, Integer size, Boolean ascending);
}
