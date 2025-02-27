package io.github.hormuth.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.validator.constraints.ISBN;

import io.github.hormuth.libraryapi.model.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record BookInputDTO(
    @ISBN
    @NotBlank(message = "field is required")
    String isbn,
    @NotBlank
    String title,
    @NotNull(message = "field is required")
    String dateOfPublication,
    Gender gender,
    BigDecimal price,
    @NotNull(message = "field is required")
    UUID authorId
) {
    
}
