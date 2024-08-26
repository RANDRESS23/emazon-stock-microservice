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
}
