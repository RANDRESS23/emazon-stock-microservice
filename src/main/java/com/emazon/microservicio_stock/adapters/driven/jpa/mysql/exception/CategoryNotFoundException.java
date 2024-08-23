package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
