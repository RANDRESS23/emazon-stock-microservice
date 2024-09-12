package com.emazon.microservicio_stock.domain.exception;

public class AlreadyExistsFieldException extends RuntimeException {
    public AlreadyExistsFieldException(String message) {
        super(message);
    }
}
