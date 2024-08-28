package com.emazon.microservicio_stock.domain.api.use_case;

import com.emazon.microservicio_stock.domain.api.IProductServicePort;
import com.emazon.microservicio_stock.domain.exception.CategoryNotFoundException;
import com.emazon.microservicio_stock.domain.exception.DuplicateCategoryException;
import com.emazon.microservicio_stock.domain.exception.InvalidProductNameException;
import com.emazon.microservicio_stock.domain.exception.ProductNotFoundException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.spi.ICategoryPersistencePort;
import com.emazon.microservicio_stock.domain.spi.IProductPersistencePort;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

import java.util.ArrayList;
import java.util.List;

public class ProductUseCase implements IProductServicePort {
    private IProductPersistencePort productPersistencePort;
    private ICategoryPersistencePort categoryPersistencePort;

    public ProductUseCase(IProductPersistencePort productPersistencePort, ICategoryPersistencePort categoryPersistencePort) {
        this.productPersistencePort = productPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void saveProduct(Product product) {
        if (productPersistencePort.getProduct(product.getName()).isPresent()) {
            throw new InvalidProductNameException(DomainConstants.PRODUCT_ALREADY_EXISTS_MESSAGE);
        }

        List<String> categoryNames = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for (Category category : product.getCategories()) {
            if (categoryNames.contains(category.getName())) {
                throw new DuplicateCategoryException(DomainConstants.DUPLICATE_CATEGORY_MESSAGE);
            }
            categoryNames.add(category.getName());

            categories.add(categoryPersistencePort.getCategory(category.getName()).orElseThrow(() ->
                    new CategoryNotFoundException(DomainConstants.CATEGORY_NOT_FOUND)
            ));
        }
        product.setCategories(categories);


        productPersistencePort.saveProduct(product);
    }

    @Override
    public void deleteProduct(String name) {
        Product product = productPersistencePort.getProduct(name)
                .orElseThrow(() -> new ProductNotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
        productPersistencePort.deleteProduct(product.getName());
    }

    @Override
    public Product getProduct(String name) {
        return productPersistencePort.getProduct(name)
                .orElseThrow(() -> new ProductNotFoundException(DomainConstants.PRODUCT_NOT_FOUND));
    }
}
