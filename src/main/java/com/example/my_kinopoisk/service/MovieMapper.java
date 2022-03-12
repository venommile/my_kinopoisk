package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    Movie toEntity(MovieDto movieDto);

}
