package com.emazon.microservicio_stock.domain.exception;

public class MaxCategoriesForProductException extends RuntimeException {
    public MaxCategoriesForProductException(String message) {
        super(message);
    }
}
