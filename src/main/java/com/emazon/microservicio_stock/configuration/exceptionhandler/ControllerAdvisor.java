package com.emazon.microservicio_stock.configuration.exceptionhandler;

import com.emazon.microservicio_stock.configuration.Constants;
import com.emazon.microservicio_stock.domain.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(NotFoundException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.NOT_FOUND_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCategoryException(DuplicateCategoryException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.DUPLICATE_CATEGORY_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(AlreadyExistsFieldException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsFieldException(AlreadyExistsFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.CONFLICT.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NegativeNotAllowedException.class)
    public ResponseEntity<ExceptionResponse> handleNegativeNotAllowedException(NegativeNotAllowedException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MinCategoriesForProductException.class)
    public ResponseEntity<ExceptionResponse> handleMinCategoriesForProductException(MinCategoriesForProductException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MaxCategoriesForProductException.class)
    public ResponseEntity<ExceptionResponse> handleMaxCategoriesForProductException(MaxCategoriesForProductException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidSortByParamException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidSortByParamException(InvalidSortByParamException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAuthorizationDeniedException(AuthorizationDeniedException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.FORBIDDEN.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(exception.getMessage()),
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now()));
    }
}
