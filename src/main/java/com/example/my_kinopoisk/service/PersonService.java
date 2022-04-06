package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entity.ParticipantFilm;
import com.example.my_kinopoisk.domain.entity.Person;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public List<Person> getPersons() {

        return StreamSupport.stream(
            personRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public PersonViewDto savePerson(PersonCreateDto personDto) {
        var foundPerson = personRepository.findByNameAndSurname(personDto.getName(), personDto.getSurname());
        var person = personMapper.toEntity(personDto);
        person.setId(foundPerson.map(Person::getId).orElse(null));
        return personMapper.toViewDto(
            personRepository.save(person)
        );
    }

    public PersonViewDto getPerson(Long id) {
        return personMapper.toViewDto(
            personRepository.findById(id).orElseThrow()
        );
    }

    public List<PersonInListDto> getPersonsOnlyDto() {
        return getPersons().stream().map(personMapper::toPersonInListDto).collect(Collectors.toList());
    }

    public Person savePersonIfExists(ParticipantFilm participant) {
        return personRepository.findByNameAndSurname(participant.getName(), participant.getSurname())
            .orElseGet(
                () -> personRepository.save(
                    new Person(participant)
                )
            );
    }
}
