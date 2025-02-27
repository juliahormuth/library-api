package io.github.hormuth.libraryapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {
    boolean existsByAuthor(Author author);

    boolean existsByIsbn(String isbn);

    Optional<Book> findByIsbn(String isbn);
}
