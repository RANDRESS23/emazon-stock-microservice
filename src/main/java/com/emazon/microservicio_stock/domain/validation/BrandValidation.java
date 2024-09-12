package com.emazon.microservicio_stock.domain.validation;

import com.emazon.microservicio_stock.domain.exception.EmptyFieldException;
import com.emazon.microservicio_stock.domain.exception.MaxLengthException;
import com.emazon.microservicio_stock.domain.model.Brand;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

public class BrandValidation {
    public void validateBrand(Brand brand) {
        validateNameBrand(brand.getName());
        validateDescriptionBrand(brand.getDescription());
    }

    private void validateNameBrand(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (name.trim().length() > DomainConstants.MAXIMUM_NAME_CHARACTERS) {
            throw new MaxLengthException(DomainConstants.Field.NAME.toString());
        }
    }

    private void validateDescriptionBrand(String description) {
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (description.trim().length() > DomainConstants.MAXIMUM_DESCRIPTION_CHARACTERS_BRAND) {
            throw new MaxLengthException(DomainConstants.Field.DESCRIPTION.toString());
        }
    }
}
