package io.github.hormuth.libraryapi.validador;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.hormuth.libraryapi.exception.ExceptionDuplicatedRegister;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.repository.AuthorRepository;

@Component
public class AuthorValidador {
    private AuthorRepository authorRepository;

    public AuthorValidador(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void validade(Author author) {
        if (authorAlreadyExists(author)) {
            throw new ExceptionDuplicatedRegister("Author already exists");
        }
    }

    private boolean authorAlreadyExists(Author author) {
        Optional<Author> authorOptional = authorRepository.findByNameAndBirthdayAndNationality(author.getName(), author.getBirthday(), author.getNationality());

        if (author.getId() == null) {
            return authorOptional.isPresent();
        }

        return !author.getId().equals(authorOptional.get().getId()) && authorOptional.isPresent();
    }
}
