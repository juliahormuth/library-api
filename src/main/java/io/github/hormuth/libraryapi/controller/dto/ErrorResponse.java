package io.github.hormuth.libraryapi.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErrorResponse(int status, String message, List<FieldError> errors) {
    public static ErrorResponse standardMessage(String message) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, List.of());
    }

    public static ErrorResponse conflict(String message) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), message, List.of());
    }
}