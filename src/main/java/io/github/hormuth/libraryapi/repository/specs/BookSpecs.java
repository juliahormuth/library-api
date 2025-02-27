package io.github.hormuth.libraryapi.repository.specs;

import org.springframework.data.jpa.domain.Specification;

import io.github.hormuth.libraryapi.model.Book;
import io.github.hormuth.libraryapi.model.Gender;

public class BookSpecs {
    public static Specification<Book> isbnEqual(String isbn) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn);
    }

    public static Specification<Book> titleLike(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Book> genderEqual(Gender gender) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("gender"), gender);
    }

    public static Specification<Book> yearOfPublicationEqual(Integer dateOfPublication) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(
                criteriaBuilder.function("to_char", String.class, root.get("dateOfPublication"), criteriaBuilder.literal("YYYY")),
                dateOfPublication.toString());
    }

    public static Specification<Book> authorNameLike(String authorName) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.upper(root.get("author").get("name")), "%" + authorName.toUpperCase() + "%");
    }
}
