package com.example.my_kinopoisk.service.mapper;


import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entity.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toEntity(PersonCreateDto personCreateDto);
    PersonCreateDto toCreateDto(Person person);

    PersonInListDto toPersonInListDto(Person person);
    Person toEntity(PersonInListDto personInListDto);

    PersonViewDto toViewDto(Person person);

    Person toEntity(PersonViewDto personViewDto);
}
