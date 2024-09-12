package com.emazon.microservicio_stock.domain.model;

import com.emazon.microservicio_stock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Category {
    private final Long categoryId;
    private final String name;
    private final String description;

    public Category(Long categoryId, String name, String description) {
        this.categoryId = categoryId;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
