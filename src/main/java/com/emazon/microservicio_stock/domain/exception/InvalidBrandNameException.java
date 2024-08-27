package com.emazon.microservicio_stock.domain.exception;

public class InvalidBrandNameException extends RuntimeException {
    public InvalidBrandNameException(String message) {
        super(message);
    }
}
