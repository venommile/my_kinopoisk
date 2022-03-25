package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonInListMapper {
    PersonInListDto toDto(Person person);
    Person toEntity(PersonInListDto personInListDto);
}
