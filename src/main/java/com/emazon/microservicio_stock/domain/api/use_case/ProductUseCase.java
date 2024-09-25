package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.exception.*;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.CustomPage;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.IBrandPersistencePort;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;
import com.emazon.microservicio_stock.domain.validation.ProductValidation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductUseCase implements IProductServicePort {
    private final IProductPersistencePort productPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IBrandPersistencePort brandPersistencePort;
    private final ProductValidation productValidation;

    public ProductUseCase(IProductPersistencePort productPersistencePort, ICategoryPersistencePort categoryPersistencePort, IBrandPersistencePort brandPersistencePort, ProductValidation productValidation) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.brandPersistencePort = brandPersistencePort;
        this.productValidation = productValidation;
    }

    @Override
    public Product saveProduct(Product product) {
        if (productPersistencePort.getProductByName(product.getName()).isPresent()) {
            throw new AlreadyExistsFieldException(DomainConstants.PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        Brand brand = brandPersistencePort.getBrandById(product.getBrand().getBrandId()).orElseThrow(() ->
                new NotFoundException(DomainConstants.BRAND_NOT_FOUND)
        );

        product.setBrand(brand);

        List<Long> categoryIds = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for (Category category : product.getCategories()) {
            if (categoryIds.contains(category.getCategoryId())) {
                throw new DuplicateCategoryException(DomainConstants.DUPLICATE_CATEGORY_MESSAGE);
            }
            categoryIds.add(category.getCategoryId());

            categories.add(categoryPersistencePort.getCategoryById(category.getCategoryId()).orElseThrow(() ->
                    new NotFoundException(DomainConstants.CATEGORY_NOT_FOUND))
            );
        }
        product.setCategories(categories);

        productValidation.validateProduct(product);
        return productPersistencePort.saveProduct(product);
    }

    @Override
    public void deleteProduct(String name) {
        Product product = productPersistencePort.getProductByName(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
        productPersistencePort.deleteProduct(product.getName());
    }

    @Override
    public Product getProduct(String name) {
        return productPersistencePort.getProductByName(name)
                .orElseThrow(() -> new NotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
    }

    @Override
    public Product getProductById(Long productId) {
        return productPersistencePort.getProductById(productId)
                .orElseThrow(() -> new NotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
    }

    @Override
    public CustomPage<Product> getAllProducts(Integer page, Integer size, Boolean ascending, String sortBy) {
        String[] sortByParams = {DomainConstants.FIELD_NAME, DomainConstants.FIELD_BRAND, DomainConstants.FIELD_CATEGORIES};

        if (Arrays.stream(sortByParams)
                .noneMatch(param -> param.equalsIgnoreCase(sortBy))) {
            throw new InvalidSortByParamException(DomainConstants.INVALID_PARAM_MESSAGE);
        }

        return productPersistencePort.getAllProducts(page, size, ascending, sortBy);
    }

    @Override
    public Product updateProductQuantity(Long productId, Long extraQuantity) {
        if (productPersistencePort.getProductById(productId).isEmpty()) {
            throw new NotFoundException(DomainConstants.PRODUCT_NOT_FOUND);
        }

        if (extraQuantity < DomainConstants.ZERO_CONSTANT) {
            throw new NegativeNotAllowedException(DomainConstants.NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE);
        }

        return productPersistencePort.updateProductQuantity(productId, extraQuantity);
    }
}
