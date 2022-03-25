package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.PersonShortDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonViewMapper {
    PersonViewDto toDto(Person person);
    Person toEntity(PersonViewDto personViewDto);
}
