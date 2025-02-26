package io.github.hormuth.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.hormuth.libraryapi.model.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuthorDTO(
    UUID id, 
    @NotBlank(message = "required field") 
    @Size(max = 100, message = "field outside standard size") 
    String name,
    @NotNull(message = "required field")
    LocalDate birthday,
    @NotBlank(message = "required field") @Size(max = 50, min = 2, message = "field outside standard size") String nationality) {

    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(name);
        author.setBirthday(birthday);
        author.setNationality(nationality);
        return author;
    }
}
