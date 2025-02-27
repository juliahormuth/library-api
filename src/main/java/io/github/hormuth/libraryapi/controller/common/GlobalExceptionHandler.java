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
import io.github.hormuth.libraryapi.exception.ExceptionDuplicatedRegister;
import io.github.hormuth.libraryapi.exception.ExceptionOperationNotPermitted;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<CustomFieldError> customFieldErrors = fieldErrors.stream().map(fe -> new CustomFieldError(fe.getField(), fe.getDefaultMessage())).toList();
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Error validation", customFieldErrors);
    }

    @ExceptionHandler(ExceptionDuplicatedRegister.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleException(ExceptionDuplicatedRegister e) {
        return ErrorResponse.conflict(e.getMessage());
    }

    @ExceptionHandler(ExceptionOperationNotPermitted.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ExceptionOperationNotPermitted e) {
        return ErrorResponse.standardMessage(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleUntreatedErrors(RuntimeException e) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred. Please, contact the support team.", List.of());
    }
}
