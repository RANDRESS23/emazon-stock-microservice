package com.emazon.microservicio_stock.domain.exception;

public class InvalidSortByParamException extends RuntimeException {
    public InvalidSortByParamException(String message) {
        super(message);
    }
}
