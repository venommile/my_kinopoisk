package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.entities.Person;
import com.example.my_kinopoisk.domain.dto.PersonShortDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonShortMapper {
    PersonShortDto toDto(Person person);
    Person toEntity(PersonShortDto personShortDto);
}
