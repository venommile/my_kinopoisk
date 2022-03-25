package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieCreateMapper {
    MovieCreateDto toDto(Movie movie);
    Movie toEntity(MovieCreateDto movieCreateDto);



}
