package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.entities.Genre;
import com.example.my_kinopoisk.domain.entities.GenreCreateDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreCreateMapper {
    GenreCreateDto toDto(Genre genre);
    Genre toEntity(GenreCreateDto genreCreateDto);

}
