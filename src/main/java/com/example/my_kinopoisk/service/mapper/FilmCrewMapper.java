package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.FilmCrewInPerson;
import com.example.my_kinopoisk.domain.dto.FilmCrewMovieViewDto;
import com.example.my_kinopoisk.domain.entity.FilmCrew;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FilmCrewMapper {


    FilmCrewMovieViewDto toMovieDto(FilmCrew filmCrew);

    FilmCrewInPerson toPersonDto(FilmCrew filmCrew);

    FilmCrew toEntity(FilmCrewMovieViewDto filmCrewMovieViewDto);

    FilmCrew toEntity(FilmCrewInPerson FilmCrewInPerson);

}
