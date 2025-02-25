package io.github.hormuth.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

import io.github.hormuth.libraryapi.model.Author;

public record AuthorDTO(UUID id, String name, LocalDate birthday, String nationality) {
    public Author mapToAuthor() {
        Author author = new Author();
        author.setName(name);
        author.setBirthday(birthday);
        author.setNationality(nationality);
        return author;
    }
}
