package io.github.hormuth.libraryapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.hormuth.libraryapi.controller.dto.AuthorDTO;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.service.AuthorService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody AuthorDTO entity) {
        var author = entity.mapToAuthor();
        authorService.save(author);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(author.getId()).toUri();

        return ResponseEntity.created(location).build();
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

}
