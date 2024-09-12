package com.emazon.microservicio_stock.domain.model;

import com.emazon.microservicio_stock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Brand {
    private final Long brandId;
    private final String name;
    private final String description;

    public Brand(Long brandId, String name, String description) {
        this.brandId = brandId;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getBrandId() {
        return brandId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
