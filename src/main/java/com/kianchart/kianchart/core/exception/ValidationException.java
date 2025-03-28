package com.kianchart.kianchart.core.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("validation error");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return this.errors;
    }
}
