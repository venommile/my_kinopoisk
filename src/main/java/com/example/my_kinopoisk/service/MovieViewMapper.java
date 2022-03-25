package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieViewMapper {
    MovieViewDto toDto(Movie movie);

    Movie toEntity(MovieViewDto movieDto);

}
