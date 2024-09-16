package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.ICategoryServicePort;
import com.emazon.microservicio_stock.domain.exception.AlreadyExistsFieldException;
import com.emazon.microservicio_stock.domain.exception.NotFoundException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.CategoryValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class CategoryUseCase implements ICategoryServicePort {
    private final ICategoryPersistencePort categoryPersistencePort;
    private final CategoryValidation categoryValidation;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort, CategoryValidation categoryValidation) {
        this.categoryPersistencePort = categoryPersistencePort;
        this.categoryValidation = categoryValidation;
    }

    @Override
    public Category saveCategory(Category category) {
        if(categoryPersistencePort.getCategory(category.getName()).isPresent()) {
            throw new AlreadyExistsFieldException(DomainConstants.CATEGORY_ALREADY_EXISTS_MESSAGE);
        }

        categoryValidation.validateCategory(category);
        return categoryPersistencePort.saveCategory(category);
    }

    @Override
    public void deleteCategory(String name) {
        Category category = categoryPersistencePort.getCategory(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
        categoryPersistencePort.deleteCategory(category.getName());
    }

    @Override
    public Category getCategory(String name) {
        return categoryPersistencePort.getCategory(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.CATEGORY_NOT_FOUND));
    }

    @Override
    public Page<Category> getAllCategories(Integer page, Integer size, Boolean ascending) {
        Sort sort = Boolean.TRUE.equals(ascending) ? Sort.by(DomainConstants.FIELD_NAME).ascending() : Sort.by(DomainConstants.FIELD_NAME).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return categoryPersistencePort.getAllCategories(pageable);
    }
}
