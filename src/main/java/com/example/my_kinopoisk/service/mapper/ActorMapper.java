package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.ActorMovieViewDto;
import com.example.my_kinopoisk.domain.entity.Actor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ActorMapper {


    ActorMovieViewDto toCreateDto(Actor actor);

    Actor toEntity(ActorMovieViewDto actorCreateDto);

}
