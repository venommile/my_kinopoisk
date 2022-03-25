package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreViewDto;
import com.example.my_kinopoisk.domain.entities.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreViewDto toDto(Genre genre);

    Genre toEntity(GenreViewDto genreViewDto);
}
