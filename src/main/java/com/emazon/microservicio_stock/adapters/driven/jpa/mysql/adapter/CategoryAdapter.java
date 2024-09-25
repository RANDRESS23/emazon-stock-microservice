package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.adapter;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.AlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.NotFoundException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util.DrivenConstants;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public Category saveCategory(Category category) {
        if (categoryRepository.findByName(category.getName()).isPresent()) {
            throw new AlreadyExistsException(DrivenConstants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE);
        }

        CategoryEntity categoryEntity = categoryRepository.save(categoryEntityMapper.toEntity(category));
        return categoryEntityMapper.toDomainModel(categoryEntity);
    }

    @Override
    public void deleteCategory(String name) {
        CategoryEntity categoryEntity = categoryRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(DrivenConstants.CATEGORY_NOT_FOUND));
        categoryRepository.delete(categoryEntity);
    }

    @Override
    public Optional<Category> getCategory(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomainModel);
    }

    @Override
    public Optional<Category> getCategoryById(Long idCategory) {
        return categoryRepository.findByCategoryId(idCategory).map(categoryEntityMapper::toDomainModel);
    }

    @Override
    public CustomPage<Category> getAllCategories(Integer page, Integer size, Boolean ascending) {
        Sort sort = Boolean.TRUE.equals(ascending) ? Sort.by(DomainConstants.FIELD_NAME).ascending() : Sort.by(DomainConstants.FIELD_NAME).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<CategoryEntity> pageCategoriesEntity = categoryRepository.findAll(pageable);
        Page<Category> pageCategories = categoryEntityMapper.toPageOfCategories(pageCategoriesEntity);

        CustomPage<Category> customPage = new CustomPage<>();
        customPage.setPageNumber(pageCategories.getNumber());
        customPage.setPageSize(pageCategories.getSize());
        customPage.setTotalElements(pageCategories.getTotalElements());
        customPage.setTotalPages(pageCategories.getTotalPages());
        customPage.setContent(pageCategories.getContent());

        return customPage;
    }
}
