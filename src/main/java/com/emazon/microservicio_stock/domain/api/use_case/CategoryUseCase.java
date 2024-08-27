package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.exception.CategoryNotFoundException;
import com.emazon.microservicio_stock.domain.exception.InvalidCategoryNameException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CategoryUseCase implements ICategoryServicePort {
    private ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveCategory(Category category) {
        if(categoryPersistencePort.getCategory(category.getName()).isPresent()) {
            throw new InvalidCategoryNameException(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }

        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public void deleteCategory(String name) {
        Category category = categoryPersistencePort.getCategory(name)
                .orElseThrow(() -> new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
        categoryPersistencePort.deleteCategory(category.getName());
    }

    @Override
    public Category getCategory(String name) {
        return categoryPersistencePort.getCategory(name)
                .orElseThrow(() -> new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
    }

    @Override
    public Page<Category> getAllCategories(Integer page, Integer size, Boolean ascending) {
        Sort sort = Boolean.TRUE.equals(ascending) ? Sort.by("name").ascending() : Sort.by("name").descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return categoryPersistencePort.getAllCategories(pageable);
    }
}
