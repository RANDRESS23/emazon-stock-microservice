package com.emazon.microservicio_stock.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        DESCRIPTION,
        PRICE,
        QUANTITY,
        CATEGORIES
    }

    public static final Integer MAXIMUM_NAME_CHARACTERS = 70;
    public static final Integer MAXIMUM_DESCRIPTION_CHARACTERS_CATEGORY = 90;
    public static final Integer MAXIMUM_DESCRIPTION_CHARACTERS_BRAND = 120;
    public static final Integer MAXIMUM_DESCRIPTION_CHARACTERS_PRODUCT = 120;
    public static final Integer MAXIMUM_CATEGORIES_FOR_PRODUCT = 3;
    public static final Integer ZERO_CONSTANT = 0;

    public static final String FIELD_NAME = "name";
    public static final String FIELD_BRAND = "brand";
    public static final String FIELD_CATEGORIES = "categories";

    public static final String SORT_BY_PRODUCT_NAME = "name";
    public static final String SORT_BY_BRAND_NAME = "brand.name";
    public static final String SORT_BY_CATEGORY_NAME = "categories.name";

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_DESCRIPTION_NULL_MESSAGE = "Field 'description' cannot be null";
    public static final String FIELD_PRICE_NULL_MESSAGE = "Field 'price' cannot be null";
    public static final String FIELD_QUANTITY_NULL_MESSAGE = "Field 'quantity' cannot be null";
    public static final String FIELD_BRAND_NULL_MESSAGE = "Field 'quantity' cannot be null";
    public static final String FIELD_CATEGORIES_NULL_MESSAGE = "Field 'categories' cannot be null";

    public static final String CATEGORY_ALREADY_EXISTS_MESSAGE = "Category already exists.";
    public static final String BRAND_ALREADY_EXISTS_MESSAGE = "Brand already exists.";
    public static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Product already exists.";

    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String BRAND_NOT_FOUND = "Brand not found.";
    public static final String PRODUCT_NOT_FOUND = "Product not found.";

    public static final String MAXIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE = "Products can have up to 3 categories maximum.";
    public static final String MINIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE = "Products must have at least 1 category as a minimum.";

    public static final String DUPLICATE_CATEGORY_MESSAGE = "Category with name duplicated.";
    public static final String INVALID_PARAM_MESSAGE = "Invalid parameter.";
}
