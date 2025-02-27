package io.github.hormuth.libraryapi.validador;

import java.util.Optional;

import org.springframework.stereotype.Component;

import io.github.hormuth.libraryapi.exception.ExceptionDuplicatedRegister;
import io.github.hormuth.libraryapi.model.Book;
import io.github.hormuth.libraryapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookValidator {
    private final BookRepository bookRepository;

    public void validate(Book book) {
        if (bookAlreadyExistsWithIsbn(book)) {
            throw new ExceptionDuplicatedRegister("Book with this Isbn already exists");
        }
    }

    private boolean bookAlreadyExistsWithIsbn(Book book) {
        Optional<Book> bookExists = bookRepository.findByIsbn(book.getIsbn());

        if (book.getId() == null) {
            return bookExists.isPresent();
        }

        return bookExists.map(Book::getId).stream().anyMatch(id -> !id.equals(book.getId()));
    }
}
