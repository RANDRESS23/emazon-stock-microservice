package com.emazon.microservicio_stock.domain.model;

import com.emazon.microservicio_stock.domain.util.DomainConstants;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class Product {
    private final Long productId;
    private final String name;
    private final String description;
    private final Long quantity;
    private final BigDecimal price;
    private List<Category> categories;
    private Brand brand;

    public Product(Long productId, String name, String description, Long quantity, BigDecimal price, List<Category> categories, Brand brand) {
        this.productId = productId;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
        this.price = requireNonNull(price, DomainConstants.FIELD_PRICE_NULL_MESSAGE);
        this.quantity = requireNonNull(quantity, DomainConstants.FIELD_QUANTITY_NULL_MESSAGE);
        this.categories = requireNonNull(categories, DomainConstants.FIELD_CATEGORIES_NULL_MESSAGE);
        this.brand = requireNonNull(brand, DomainConstants.FIELD_BRAND_NULL_MESSAGE);
    }

    public Long getProductId() {
        return productId;
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
