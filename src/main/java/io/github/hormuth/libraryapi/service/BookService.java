package io.github.hormuth.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.github.hormuth.libraryapi.model.Book;
import io.github.hormuth.libraryapi.model.Gender;
import io.github.hormuth.libraryapi.repository.BookRepository;
import io.github.hormuth.libraryapi.repository.specs.BookSpecs;
import io.github.hormuth.libraryapi.validador.BookValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    public Book save(Book book) {
        bookValidator.validate(book);
        return bookRepository.save(book);
    }

    public Optional<Book> findById(UUID id) {
        return bookRepository.findById(id);
    }

    public void delete(Book book) {
        bookRepository.delete(book);
    }

    public List<Book> findAll(String isbn, String title, String authorName, Gender gender, Integer yearOfPublication) {

        Specification<Book> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());

        if (isbn != null) {
            spec = spec.and(BookSpecs.isbnEqual(isbn));
        }

        if (title != null) {
            spec = spec.and(BookSpecs.titleLike(title));
        }

        if (gender != null) {
            spec = spec.and(BookSpecs.genderEqual(gender));
        }

        if (yearOfPublication != null) {
            spec = spec.and(BookSpecs.yearOfPublicationEqual(yearOfPublication));
        }

        if (authorName != null) {
            spec = spec.and(BookSpecs.authorNameLike(authorName));
        }

        return bookRepository.findAll(BookSpecs.isbnEqual(isbn));
    }

    public void update(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book id is required");
        }
        bookValidator.validate(book);
        bookRepository.save(book);
    }
}
