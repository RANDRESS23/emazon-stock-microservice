package com.emazon.microservicio_stock.domain.validation;

import com.emazon.microservicio_stock.domain.exception.*;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.model.Product;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;

public class ProductValidation {
    public void validateProduct(Product product) {
        validateNameProduct(product.getName());
        validateDescriptionProduct(product.getDescription());
        validatePriceProduct(product.getPrice());
        validateQuantityProduct(product.getQuantity());
        validateCategoriesProduct(product.getCategories());
    }

    private void validateNameProduct(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (name.trim().length() > DomainConstants.MAXIMUM_NAME_CHARACTERS) {
            throw new MaxLengthException(DomainConstants.Field.NAME.toString());
        }
    }

    private void validateDescriptionProduct(String description) {
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (description.trim().length() > DomainConstants.MAXIMUM_DESCRIPTION_CHARACTERS_PRODUCT) {
            throw new MaxLengthException(DomainConstants.Field.DESCRIPTION.toString());
        }
    }

    private void validatePriceProduct(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < DomainConstants.ZERO_CONSTANT) {
            throw new NegativeNotAllowedException(DomainConstants.Field.PRICE.toString());
        }
    }

    private void validateQuantityProduct(Long quantity) {
        if (quantity < DomainConstants.ZERO_CONSTANT) {
            throw new NegativeNotAllowedException(DomainConstants.Field.QUANTITY.toString());
        }
    }

    private void validateCategoriesProduct(List<Category> categories) {
        if (categories.isEmpty()) {
            throw new MinCategoriesForProductException(DomainConstants.MINIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE);
        }

        if (categories.size() > DomainConstants.MAXIMUM_CATEGORIES_FOR_PRODUCT) {
            throw new MaxCategoriesForProductException(DomainConstants.MAXIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE);
        }
    }
}
