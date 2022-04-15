package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entity.ParticipantFilm;
import com.example.my_kinopoisk.domain.entity.Person;
import com.example.my_kinopoisk.domain.entity.Person_;
import com.example.my_kinopoisk.exception.PersonNotFoundException;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public List<Person> getPersons(Pageable pageable) {
        return StreamSupport.stream(
            personRepository
                .findAll(pageable)
                .spliterator(),
            false).collect(Collectors.toList());
    }

    public void deletePerson(Long id) {
        getPerson(id);
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

    public PersonViewDto getPersonDto(Long id) {
        return personMapper.toViewDto(
            getPerson(id)
        );
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public List<PersonInListDto> getPersonsOnlyDto(Pageable pageable) {
        return getPersons(pageable).stream().map(personMapper::toPersonInListDto).collect(Collectors.toList());
    }

    public Person savePersonIfExists(ParticipantFilm participant) {
        return personRepository.findByNameAndSurname(participant.getName(), participant.getSurname())
            .orElseGet(
                () -> personRepository.save(
                    new Person(participant)
                )
            );
    }

    public Page<Person> search(String firstName, String lastName, Pageable pageable) {
        return personRepository.findAll(Specification.where(
            hasFirstName(firstName)).and(hasLastName(lastName)), pageable);
    }


    /*
    You should at first compile to generate Person_ class

     */
    private Specification<Person> hasLastName(String surname) {
        if (surname != null && surname.length() > 0) {
            return (root, query, cb) -> cb.like(cb.upper(root.get(Person_.surname)), surname.toUpperCase() + "%");
        }
        return null;
    }

    private Specification<Person> hasFirstName(String name) {
        if (name != null && name.length() > 0) {
            return (root, query, cb) -> cb.like(cb.upper(root.get(Person_.name)), name.toUpperCase() + "%");
        }
        return null;
    }
}
