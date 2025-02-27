package io.github.hormuth.libraryapi.controller.mappers;

import org.mapstruct.Mapper;

import io.github.hormuth.libraryapi.controller.dto.AuthorDTO;
import io.github.hormuth.libraryapi.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author toEntity(AuthorDTO dto);

    AuthorDTO toDTO(Author entity);
}
