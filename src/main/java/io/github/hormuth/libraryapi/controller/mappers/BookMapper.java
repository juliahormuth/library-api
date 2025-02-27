package io.github.hormuth.libraryapi.controller.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.hormuth.libraryapi.controller.dto.BookInputDTO;
import io.github.hormuth.libraryapi.controller.dto.BookOutputDTO;
import io.github.hormuth.libraryapi.model.Book;
import io.github.hormuth.libraryapi.repository.AuthorRepository;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class })
public abstract class BookMapper {

    @Autowired
    AuthorRepository authorRepository;

    @Mapping(target = "author", expression = "java(authorRepository.findById(dto.authorId()).orElse(null))")
    public abstract Book toEntity(BookInputDTO dto);

    public abstract BookOutputDTO toDto(Book entity);
}
