package com.emazon.microservicio_stock.domain.model;

import com.emazon.microservicio_stock.domain.exception.*;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Product {
    private final Long idProduct;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;
    private List<Category> categories;
    private Brand brand;

    public Product(Long idProduct, String name, String description, Long quantity, BigDecimal price, List<Category> categories, Brand brand) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (name.trim().length() > DomainConstants.MAXIMUM_NAME_CHARACTERS) {
            throw new MaxLengthException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().length() > DomainConstants.MAXIMUM_DESCRIPTION_CHARACTERS_PRODUCT) {
            throw new MaxLengthException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new NegativeNotAllowedException(DomainConstants.Field.PRICE.toString());
        }

        if (quantity < 0) {
            throw new NegativeNotAllowedException(DomainConstants.Field.QUANTITY.toString());
        }

        if (categories.isEmpty()) {
            throw new MinCategoriesForProductException(DomainConstants.Field.CATEGORIES.toString());
        }

        if (categories.size() > DomainConstants.MAXIMUM_CATEGORIES_FOR_PRODUCT) {
            throw new MaxCategoriesForProductException(DomainConstants.Field.CATEGORIES.toString());
        }

        this.idProduct = idProduct;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.price = requireNonNull(price, DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        this.quantity = requireNonNull(quantity, DomainConstants.FIELD_QUANTITY_NULL_MESSAGE);
        this.categories = requireNonNull(categories, DomainConstants.FIELD_CATEGORIES_NULL_MESSAGE);
        this.brand = requireNonNull(brand, DomainConstants.FIELD_BRAND_NULL_MESSAGE);
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
