package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.CategoryNotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.microservicio_stock.configuration.Constants;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new CategoryAlreadyExistsException();
        }

        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public void deleteCategory(String name) {
        CategoryEntity categoryEntity = categoryRepository.findByName(name)
                .orElseThrow(() -> new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND));
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomainModel);
    }

    @Override
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomainModel);
    }
}
