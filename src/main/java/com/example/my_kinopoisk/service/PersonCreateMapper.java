package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonCreateMapper {
    Person toEntity(PersonCreateDto personCreateDto);
    PersonCreateDto toDto(Person person);
}
