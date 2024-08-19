package com.emazon.microservicio_stock.domain.exception;

public class MaxLengthException extends RuntimeException {
    public MaxLengthException(String message) {
        super(message);
    }
}
