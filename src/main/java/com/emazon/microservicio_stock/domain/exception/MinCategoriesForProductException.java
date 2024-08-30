package com.emazon.microservicio_stock.domain.exception;

public class MinCategoriesForProductException extends RuntimeException {
    public MinCategoriesForProductException(String message) {
        super(message);
    }
}
