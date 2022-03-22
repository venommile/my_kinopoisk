package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.ShortMovieDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//nullpropertymappingstrategy
public interface MovieShortMapper {
    ShortMovieDto toDto(Movie movie);

    Movie toEntity(ShortMovieDto shortMovieDto);

}
