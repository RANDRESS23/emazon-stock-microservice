package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Category;
import org.springframework.data.domain.Page;

public interface ICategoryServicePort {
    void saveCategory(Category category);
    void deleteCategory(String name);
    Category getCategory(String name);
    Page<Category> getAllCategories(Integer page, Integer size, Boolean ascending);
}
