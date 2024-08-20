package com.emazon.microservicio_stock.domain.api;

import com.emazon.microservicio_stock.domain.model.Category;

public interface ICategoryServicePort {
    void saveCategory(Category category);
}
