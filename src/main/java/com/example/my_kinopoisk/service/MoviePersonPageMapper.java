package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.entities.Movie;
import com.example.my_kinopoisk.domain.entities.MoviePersonPageDto;

public interface MoviePersonPageMapper {
    Movie toEntity(MoviePersonPageDto moviePersonPageDto);
    MoviePersonPageDto toDto(Movie movie);
}
