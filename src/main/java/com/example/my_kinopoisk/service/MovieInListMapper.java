package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//nullpropertymappingstrategy
public interface MovieInListMapper {
    MovieInListDto toDto(Movie movie);

    Movie toEntity(MovieInListDto movieInListDto);

}
