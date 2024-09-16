package com.emazon.microservicio_stock.configuration;

public class Constants {
    private Constants(){
        throw new IllegalStateException("utility class");
    }

    public static final String EMPTY_FIELD_EXCEPTION_MESSAGE = "Field %s can not be empty";
    public static final String MAX_LENGTH_EXCEPTION_MESSAGE = "The %s field exceeded the maximum character limit";
    public static final String NOT_FOUND_EXCEPTION_MESSAGE = "%s was not found";
    public static final String DUPLICATE_CATEGORY_MESSAGE = "Category with name duplicated.";
    public static final String AUTHORIZATION_HEADER  = "Authorization";
    public static final String BEARER_HEADER  = "Bearer ";
    public static final String ROLE_FIELD  = "role";
}
