package com.emazon.microservicio_stock.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION
    }

    public static final Integer MAXIMUM_NAME_CHARACTERES = 50;
    public static final Integer MAXIMUM_DESCRIPTION_CHARACTERES = 90;

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
}
