package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.util;

public class DrivenConstants {
    private DrivenConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category you want to create already exists";
    public static final String BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The brand you want to create already exists";
    public static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Product already exists.";

    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String BRAND_NOT_FOUND = "Brand not found.";
    public static final String PRODUCT_NOT_FOUND = "Product not found.";

    public static final String FIELD_NAME_NOT_BLANK_MESSAGE = "Field 'name' cannot be null.";
    public static final String FIELD_DESCRIPTION_NOT_BLANK_MESSAGE = "Field 'description' cannot be null.";

    public static final String PRODUCT_CATEGORY_TABLE_NAME = "product_category";
    public static final String BRAND_TABLE_NAME = "brand";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String PRODUCT_TABLE_NAME = "product";

    public static final String COLUMN_PRODUCT_ID = "product_id";
    public static final String COLUMN_CATEGORY_ID = "category_id";
    public static final String COLUMN_BRAND_ID = "brand_id";
}
