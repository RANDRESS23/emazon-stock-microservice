package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
