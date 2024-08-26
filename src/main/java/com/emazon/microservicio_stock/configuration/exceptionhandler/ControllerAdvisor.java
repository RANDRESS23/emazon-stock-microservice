package com.emazon.microservicio_stock.configuration.exceptionhandler;

import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.BrandAlreadyExistsException;
import com.emazon.microservicio_stock.adapters.driven.jpa.mysql.exception.CategoryAlreadyExistsException;
import com.emazon.microservicio_stock.configuration.Constants;
import com.emazon.microservicio_stock.domain.exception.BrandNotFoundException;
import com.emazon.microservicio_stock.domain.exception.CategoryNotFoundException;
import com.emazon.microservicio_stock.domain.exception.EmptyFieldException;
import com.emazon.microservicio_stock.domain.exception.MaxLengthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EMPTY_FIELD_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MaxLengthException.class)
    public ResponseEntity<ExceptionResponse> handleMaxLengthException(MaxLengthException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.MAX_LENGTH_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.CATEGORY_ALREADY_EXISTS_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.CATEGORY_NOT_FOUND, exception.getMessage()),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BrandAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleBrandAlreadyExistsException(BrandAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.BRAND_ALREADY_EXISTS_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleBrandNotFoundException(BrandNotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.BRAND_NOT_FOUND, exception.getMessage()),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }
}
