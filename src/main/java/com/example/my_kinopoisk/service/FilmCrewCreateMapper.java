package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.FilmCrewCreateDto;
import com.example.my_kinopoisk.domain.entities.FilmCrew;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmCrewCreateMapper {
    FilmCrewCreateDto toDto(FilmCrew filmCrew);
    FilmCrew toEntity(FilmCrewCreateDto filmCrewWorkerDto);
}
