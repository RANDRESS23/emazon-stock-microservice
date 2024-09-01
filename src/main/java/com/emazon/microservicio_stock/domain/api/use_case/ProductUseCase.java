package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.exception.*;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductUseCase implements IProductServicePort {
    private IProductPersistencePort productPersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;
    private IBrandPersistencePort brandPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        if (productPersistencePort.getProductByName(product.getName()).isPresent()) {
            throw new InvalidProductNameException(DomainConstants.PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        if (product.getCategories().isEmpty()) {
            throw new MinCategoriesForProductException(DomainConstants.MINIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE);
        }

        if (product.getCategories().size() > DomainConstants.MAXIMUM_CATEGORIES_FOR_PRODUCT) {
            throw new MaxCategoriesForProductException(DomainConstants.MAXIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE);
        }

        Brand brand = brandPersistencePort.getBrandById(product.getBrand().getIdBrand()).orElseThrow(() ->
                new BrandNotFoundException(DomainConstants.BRAND_NOT_FOUND)
        );

        product.setBrand(brand);

        List<Long> categoryIds = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for (Category category : product.getCategories()) {
            if (categoryIds.contains(category.getIdCategory())) {
                throw new DuplicateCategoryException(DomainConstants.DUPLICATE_CATEGORY_MESSAGE);
            }
            categoryIds.add(category.getIdCategory());

            categories.add(categoryPersistencePort.getCategoryById(category.getIdCategory()).orElseThrow(() ->
                    new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND))
            );
        }
        product.setCategories(categories);

        productPersistencePort.saveProduct(product);
    }

    @Override
    public void deleteProduct(String name) {
        Product product = productPersistencePort.getProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
        productPersistencePort.deleteProduct(product.getName());
    }

    @Override
    public Product getProduct(String name) {
        return productPersistencePort.getProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
    }

    @Override
    public Page<Product> getAllProducts(Integer page, Integer size, Boolean ascending, String sortBy) {
        String[] sortByParams = {DomainConstants.FIELD_NAME, DomainConstants.FIELD_BRAND, DomainConstants.FIELD_CATEGORIES};

        if (Arrays.stream(sortByParams)
                .noneMatch(param -> param.equalsIgnoreCase(sortBy))) {
            throw new InvalidSortByParamException(DomainConstants.INVALID_PARAM_MESSAGE);
        }

        String sortByFinal = null;

        switch (sortBy) {
            case DomainConstants.FIELD_BRAND -> sortByFinal = DomainConstants.SORT_BY_BRAND_NAME;
            case DomainConstants.FIELD_CATEGORIES -> sortByFinal = DomainConstants.SORT_BY_CATEGORY_NAME;
            default -> sortByFinal = DomainConstants.SORT_BY_PRODUCT_NAME;
        }

        Sort sort = Boolean.TRUE.equals(ascending) ? Sort.by(sortByFinal).ascending() : Sort.by(sortByFinal).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return productPersistencePort.getAllProducts(pageable);
    }
}
