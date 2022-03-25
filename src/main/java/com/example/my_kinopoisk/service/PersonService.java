package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entities.ParticipantFilm;
import com.example.my_kinopoisk.domain.entities.Person;
import com.example.my_kinopoisk.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonInListMapper personInListMapper;
    private final PersonViewMapper personViewMapper;
    private final PersonCreateMapper personCreateMapper;


    public List<Person> getPersons() {

        return StreamSupport.stream(
            personRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public PersonViewDto savePerson(PersonCreateDto personCreateDto) {
        var foundPerson = personRepository.findByNameAndSurname(personCreateDto.getName(), personCreateDto.getSurname());

        if (foundPerson.isEmpty()) {
//            var mPerson = ;
//            mPerson.setActorRoles(mPerson.getActorRoles());//to Eager
//            mPerson.setFilmCrewRoles(mPerson.getFilmCrewRoles());
            return personViewMapper.toDto(
                personRepository.save(personRepository.save(personCreateMapper.toEntity(personCreateDto)))
            );
        }
        var person = personCreateMapper.toEntity(personCreateDto);
        person.setId(foundPerson.get().getId());
        return personViewMapper.toDto(
            personRepository.save(person)
        );

    }

    public PersonViewDto getPerson(Long id) {
        return personViewMapper.toDto(
            personRepository.findById(id).orElseThrow()
        );
    }

    public List<PersonInListDto> getPersonsOnlyDto() {
        return getPersons().stream().map(personInListMapper::toDto).collect(Collectors.toList());
    }

    public Person savePersonIfExists(ParticipantFilm participant) {
        Person p = new Person();//УБРАТЬ
        p.setName(participant.getName());
        p.setSurname(participant.getSurname());
        var foundPerson =  personRepository.findByNameAndSurname(
            participant.getName(), participant.getSurname());
        if (foundPerson.isEmpty()){
            return personRepository.save(p);
        }
        return foundPerson.get();
    }
}
