package com.example.my_kinopoisk.service.mapper;

import com.example.my_kinopoisk.domain.dto.GenreCreateDto;
import com.example.my_kinopoisk.domain.dto.GenreViewDto;
import com.example.my_kinopoisk.domain.entity.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreViewDto toViewDto(Genre genre);

    Genre toEntity(GenreViewDto genreViewDto);

    Genre toEntity(GenreCreateDto genreCreateDto);

    GenreCreateDto toCreateDto(Genre genre);

}
