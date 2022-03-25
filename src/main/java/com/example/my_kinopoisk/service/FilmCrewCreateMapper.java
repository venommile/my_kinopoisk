package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.ActorCreateDto;
import com.example.my_kinopoisk.domain.dto.FilmCrewWorkerCreateDto;
import com.example.my_kinopoisk.domain.entities.Actor;
import com.example.my_kinopoisk.domain.entities.FilmCrewWorker;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FilmCrewCreateMapper {
    FilmCrewWorkerCreateDto toDto(FilmCrewWorker filmCrew);
    FilmCrewWorker toEntity(FilmCrewWorkerCreateDto filmCrewWorkerDto);
}
