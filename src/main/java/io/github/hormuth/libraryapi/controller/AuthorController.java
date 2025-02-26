package io.github.hormuth.libraryapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.hormuth.libraryapi.controller.dto.AuthorDTO;
import io.github.hormuth.libraryapi.controller.dto.ErrorResponse;
import io.github.hormuth.libraryapi.exception.ExceptionDuplicatedRegister;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.service.AuthorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping()
    public ResponseEntity<Object> save(@RequestBody AuthorDTO entity) {
        try {
            var author = entity.mapToAuthor();
            authorService.save(author);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId()).toUri();

            return ResponseEntity.created(location).build();
        } catch (ExceptionDuplicatedRegister e) {
            var errorDto = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDto.status()).body(errorDto.message());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
        var idAuhor = UUID.fromString(id);
        Optional<Author> author = authorService.findById(idAuhor);
        if (author.isPresent()) {
            return ResponseEntity.ok(new AuthorDTO(author.get().getId(), author.get().getName(), author.get().getBirthday(), author.get().getNationality()));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        var idAuhor = UUID.fromString(id);
        Optional<Author> author = authorService.findById(idAuhor);
        if (author.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        authorService.delete(author.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    public ResponseEntity<List<AuthorDTO>> findAllByNameAndNationality(@RequestParam(value = "name") String name,
            @RequestParam(value = "nationality") String nationality) {
        List<Author> result = authorService.findAllByNameAndNationality(name, nationality);
        List<AuthorDTO> authors = result.stream().map(author -> new AuthorDTO(author.getId(), author.getName(), author.getBirthday(), author.getNationality()))
                .toList();
        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") String id, @RequestBody AuthorDTO entity) {
        try {
            var idAuhor = UUID.fromString(id);
            Optional<Author> authorExists = authorService.findById(idAuhor);

            if (authorExists.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            var author = authorExists.get();
            author.setName(entity.name());
            author.setBirthday(entity.birthday());

            authorService.updateById(author);

            return ResponseEntity.noContent().build();
        } catch (ExceptionDuplicatedRegister e) {
            var errorDto = ErrorResponse.conflict(e.getMessage());
            return ResponseEntity.status(errorDto.status()).body(errorDto.message());
        }
    }

}
