package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.FilmCrewCreateDto;
import com.example.my_kinopoisk.domain.entity.FilmCrew;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmCrewMapper {
    FilmCrewCreateDto toDto(FilmCrew filmCrew);
    FilmCrew toEntity(FilmCrewCreateDto filmCrewWorkerDto);
}
