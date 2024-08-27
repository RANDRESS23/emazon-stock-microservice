package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception;

public class BrandNotFoundException extends RuntimeException {
    public BrandNotFoundException(String message) {
        super(message);
    }
}
