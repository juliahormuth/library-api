package io.github.hormuth.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.util.UUID;

import io.github.hormuth.libraryapi.model.Gender;

public record BookOutputDTO(
    UUID id,
    String isbn,
    String title,
    String dateOfPublication,
    Gender gender,
    BigDecimal price,
    AuthorDTO author
) {}
