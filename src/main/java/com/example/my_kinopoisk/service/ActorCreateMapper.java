package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.ActorCreateDto;
import com.example.my_kinopoisk.domain.entities.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorCreateMapper {
    ActorCreateDto toDto(Actor actor);
    Actor toEntity(ActorCreateDto actorCreateDto);

}
