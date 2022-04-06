package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.ActorCreateDto;
import com.example.my_kinopoisk.domain.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {


    ActorCreateDto toCreateDto(Actor actor);
    Actor toEntity(ActorCreateDto actorCreateDto);

}
