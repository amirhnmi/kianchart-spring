package com.kianchart.kianchart.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ValidationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
        errorResponse.put("message", ex.getMessage());
        errorResponse.put("error", ex.getErrors());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(DuplicationException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicationException(DuplicationException ex) {
        Map<String, Object> errorResponse = new HashMap<>();

        errorResponse.put("status", HttpStatus.CONFLICT.value());
        errorResponse.put("error", "Duplication Error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
}