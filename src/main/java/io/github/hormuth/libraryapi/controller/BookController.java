package io.github.hormuth.libraryapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.hormuth.libraryapi.controller.dto.BookInputDTO;
import io.github.hormuth.libraryapi.controller.dto.BookOutputDTO;
import io.github.hormuth.libraryapi.controller.mappers.BookMapper;
import io.github.hormuth.libraryapi.model.Book;
import io.github.hormuth.libraryapi.model.Gender;
import io.github.hormuth.libraryapi.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController implements GenericController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid BookInputDTO input) {
        Book book = bookMapper.toEntity(input);
        bookService.save(book);
        var url = generateHeaderLocation(book.getId());
        return ResponseEntity.created(url).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOutputDTO> findById(@PathVariable("id") UUID id) {
        return bookService.findById(id).map(book -> {
            var dto = bookMapper.toDto(book);
            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        return bookService.findById(UUID.fromString(id)).map(book -> {
            bookService.delete(book);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<BookOutputDTO>> findAll(@RequestParam(value = "isbn", required = false) String isbn,
            @RequestParam(value = "title", required = false) String title, @RequestParam(value = "authorName", required = false) String authorName,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "yearOfPublication", required = false) Integer yearOfPublication

    ) {
        var result = bookService.findAll(isbn, title, authorName, gender, yearOfPublication);
        var list = result.stream().map(bookMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody BookInputDTO input) {
        return bookService.findById(UUID.fromString(id)).map(book -> {
            Book auxiliarEntity = bookMapper.toEntity(input);

            book.setDateOfPublication(auxiliarEntity.getDateOfPublication());
            book.setIsbn(auxiliarEntity.getIsbn());
            book.setTitle(auxiliarEntity.getTitle());
            book.setGender(auxiliarEntity.getGender());
            book.setPrice(auxiliarEntity.getPrice());
            book.setAuthor(auxiliarEntity.getAuthor());

            bookService.update(book);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
