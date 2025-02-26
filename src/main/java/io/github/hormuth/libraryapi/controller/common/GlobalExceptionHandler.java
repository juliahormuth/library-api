package io.github.hormuth.libraryapi.controller.common;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.hormuth.libraryapi.controller.dto.CustomFieldError;
import io.github.hormuth.libraryapi.controller.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<CustomFieldError> customFieldErrors = fieldErrors.stream().map(fe -> new CustomFieldError(fe.getField(), fe.getDefaultMessage())).toList();
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Error validation", customFieldErrors);
    }
}
