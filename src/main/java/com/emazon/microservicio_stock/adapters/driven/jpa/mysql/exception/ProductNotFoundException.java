package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
