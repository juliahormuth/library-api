package io.github.hormuth.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import io.github.hormuth.libraryapi.exception.ExceptionOperationNotPermitted;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.repository.AuthorRepository;
import io.github.hormuth.libraryapi.repository.BookRepository;
import io.github.hormuth.libraryapi.validador.AuthorValidador;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorValidador authorValidador;

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

    public List<Author> findAllByExample(String name, String nationality) {
        Author author = new Author();
        author.setName(name);
        author.setNationality(nationality);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Author> example = Example.of(author, matcher);
        return authorRepository.findAll(example);
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
