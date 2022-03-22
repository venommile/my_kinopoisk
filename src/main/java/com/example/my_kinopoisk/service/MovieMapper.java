package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
//nullpropertymappingstrategy
public interface MovieMapper {
    MovieDto toDto(Movie movie);

    Movie toEntity(MovieDto movieDto);

}
