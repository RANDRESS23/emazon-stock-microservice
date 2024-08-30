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

import java.util.ArrayList;
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
}
