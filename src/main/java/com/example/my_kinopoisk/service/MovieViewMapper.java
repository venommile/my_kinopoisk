package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Service;

@Mapper(componentModel = "spring")
public interface MovieViewMapper {
    MovieViewDto toDto(Movie movie);
    Movie toEntity(MovieViewDto movieDto);

}
