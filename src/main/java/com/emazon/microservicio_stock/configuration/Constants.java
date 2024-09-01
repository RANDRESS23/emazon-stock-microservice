package com.emazon.microservicio_stock.configuration;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The category you want to create already exists";
    public static final String BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE = "The brand you want to create already exists";
    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String MAX_LENGTH_EXCEPTION_MESSAGE = "The %s field exceeded the maximum character limit";
    public static final String CATEGORY_NOT_FOUND = "Category not found.";
    public static final String BRAND_NOT_FOUND = "Brand not found.";
    public static final String PRODUCT_ALREADY_EXISTS_MESSAGE = "Product already exists.";
    public static final String PRODUCT_NOT_FOUND = "Product not found.";
    public static final String DUPLICATE_CATEGORY_MESSAGE = "Category with name duplicated.";
    public static final String NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE = "Negative exception not allowed.";
    public static final String MAXIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE = "Products can have up to 3 categories maximum.";
    public static final String MINIMUM_CATEGORIES_FOR_PRODUCT_MESSAGE = "Products must have at least 1 category as a minimum.";
    public static final String INVALID_PRODUCT_SORT_PARAM_MESSAGE = "Invalid sorting parameter, sorting parameters for product are: name, brand and categories";
}
