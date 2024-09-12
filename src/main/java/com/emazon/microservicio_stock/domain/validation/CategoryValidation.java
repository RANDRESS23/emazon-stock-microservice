package com.emazon.microservicio_stock.domain.validation;

import com.emazon.microservicio_stock.domain.exception.EmptyFieldException;
import com.emazon.microservicio_stock.domain.exception.MaxLengthException;
import com.emazon.microservicio_stock.domain.model.Category;
import com.emazon.microservicio_stock.domain.util.DomainConstants;

public class CategoryValidation {
    public void validateCategory(Category category) {
        validateNameCategory(category.getName());
        validateDescriptionCategory(category.getDescription());
    }

    private void validateNameCategory(String name) {
        if (name.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.NAME.toString());
        }

        if (name.trim().length() > DomainConstants.MAXIMUM_NAME_CHARACTERS) {
            throw new MaxLengthException(DomainConstants.Field.NAME.toString());
        }
    }

    private void validateDescriptionCategory(String description) {
        if (description.trim().isEmpty()) {
            throw new EmptyFieldException(DomainConstants.Field.DESCRIPTION.toString());
        }

        if (description.trim().length() > DomainConstants.MAXIMUM_DESCRIPTION_CHARACTERS_CATEGORY) {
            throw new MaxLengthException(DomainConstants.Field.DESCRIPTION.toString());
        }
    }
}
