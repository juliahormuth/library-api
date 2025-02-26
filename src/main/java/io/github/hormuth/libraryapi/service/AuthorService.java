package io.github.hormuth.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.github.hormuth.libraryapi.exception.ExceptionOperationNotPermitted;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.repository.AuthorRepository;
import io.github.hormuth.libraryapi.repository.BookRepository;
import io.github.hormuth.libraryapi.validador.AuthorValidador;

@Service
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private AuthorValidador authorValidador;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository, AuthorValidador authorValidador) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.authorValidador = authorValidador;
    }

    public Author save(Author author) {
        authorValidador.validade(author);
        return authorRepository.save(author);
    }

    public Optional<Author> findById(UUID id) {
        return authorRepository.findById(id);
    }

    public void delete(Author author) {
        if (existsBook(author)) {
            throw new ExceptionOperationNotPermitted("Author has books associated");
        }
        authorRepository.delete(author);
    }

    public List<Author> findAllByNameAndNationality(String name, String nationality) {
        if ((name != null && !name.equals("")) && (nationality != null && !nationality.equals(""))) {
            return authorRepository.findByNameAndNationality(name, nationality);
        }
        if (name != null && !name.equals("")) {
            return authorRepository.findByName(name);
        }

        if (nationality != null && !nationality.equals("")) {
            return authorRepository.findByNationality(nationality);
        }
        return authorRepository.findAll();
    }

    public void updateById(Author author) {
        if (author.getId() == null) {
            throw new IllegalArgumentException("Author id is required");
        }
        authorValidador.validade(author);
        authorRepository.save(author);
    }

    public boolean existsBook(Author author) {
        return bookRepository.existsByAuthor(author);
    }
}
