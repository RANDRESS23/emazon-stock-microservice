package com.emazon.microservicio_stock.domain.spi;

import com.emazon.microservicio_stock.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
}
