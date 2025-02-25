package io.github.hormuth.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.hormuth.libraryapi.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

}
