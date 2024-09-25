package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;

public interface ICategoryServicePort {
    Category saveCategory(Category category);
    void deleteCategory(String name);
    Category getCategory(String name);
    CustomPage<Category> getAllCategories(Integer page, Integer size, Boolean ascending);
}
