package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MoviePersonPageDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entity.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieCreateDto toCreateDto(Movie movie);

    Movie toEntity(MovieCreateDto movieCreateDto);

    MovieInListDto toInListDto(Movie movie);

    Movie toEntity(MovieInListDto movieInListDto);

    Movie toEntity(MoviePersonPageDto moviePersonPageDto);

    MoviePersonPageDto toInPersonPageDto(Movie movie);

    MovieViewDto toViewDto(Movie movie);

    Movie toEntity(MovieViewDto movieDto);

}
