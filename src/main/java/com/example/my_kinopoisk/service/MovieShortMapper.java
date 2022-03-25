package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieShortDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//nullpropertymappingstrategy
public interface MovieShortMapper {
    MovieShortDto toDto(Movie movie);

    Movie toEntity(MovieShortDto movieShortDto);

}
