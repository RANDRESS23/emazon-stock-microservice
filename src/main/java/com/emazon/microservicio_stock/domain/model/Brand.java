package com.emazon.microservicio_stock.domain.model;

import com.emazon.microservicio_stock.domain.exception.EmptyFieldException;
import com.emazon.microservicio_stock.domain.exception.MaxLengthException;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

import static java.util.Objects.requireNonNull;

public class Brand {
    private final Long idBrand;
    private final String name;
    private final String description;

    public Brand(Long idBrand, String name, String description) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (name.trim().length() > DomainConstants.MAXIMUM_NAME_CHARACTERES) {
            throw new MaxLengthException(DomainConstants.Field.NAME.toString());
        }

        if (description.trim().length() > DomainConstants.MAXIMUM_DESCRIPTION_CHARACTERES_BRAND) {
            throw new MaxLengthException(DomainConstants.Field.DESCRIPTION.toString());
        }

        this.idBrand = idBrand;
        this.name = requireNonNull(name, DomainConstants.FIELD_NAME_NULL_MESSAGE);
        this.description = requireNonNull(description, DomainConstants.FIELD_DESCRIPTION_NULL_MESSAGE);
    }

    public Long getIdBrand() {
        return idBrand;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
