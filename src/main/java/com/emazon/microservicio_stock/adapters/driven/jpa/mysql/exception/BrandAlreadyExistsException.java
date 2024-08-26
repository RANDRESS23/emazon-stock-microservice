package com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception;

public class BrandAlreadyExistsException extends RuntimeException {
    public BrandAlreadyExistsException(String message) {
        super(message);
    }
}
