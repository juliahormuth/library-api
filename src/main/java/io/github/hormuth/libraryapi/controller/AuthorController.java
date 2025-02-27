package io.github.hormuth.libraryapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.hormuth.libraryapi.controller.dto.AuthorDTO;
import io.github.hormuth.libraryapi.controller.mappers.AuthorMapper;
import io.github.hormuth.libraryapi.model.Author;
import io.github.hormuth.libraryapi.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController implements GenericController {

    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @PostMapping()
    public ResponseEntity<Void> save(@RequestBody @Valid AuthorDTO input) {

        Author author = authorMapper.toEntity(input);
        authorService.save(author);

        URI location = generateHeaderLocation(author.getId());

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> findById(@PathVariable("id") String id) {
        var idAuhor = UUID.fromString(id);

        return authorService.findById(idAuhor).map(author -> ResponseEntity.ok(authorMapper.toDTO(author))).orElse(ResponseEntity.notFound().build());
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
        List<Author> result = authorService.findAllByExample(name, nationality);
        List<AuthorDTO> authors = result.stream().map(authorMapper::toDTO).toList();

        return ResponseEntity.ok(authors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") String id, @RequestBody AuthorDTO entity) {

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

    }

}
