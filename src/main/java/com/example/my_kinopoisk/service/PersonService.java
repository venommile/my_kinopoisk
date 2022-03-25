package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.PersonShortDto;
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
    private final PersonShortMapper personShortMapper;
    private final PersonViewMapper personViewMapper;


    public List<Person> getPersons() {

        return StreamSupport.stream(
            personRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }

    public PersonViewDto getPerson(Long id) {
        return personViewMapper.toDto(
            personRepository.findById(id).orElseThrow()
        );
    }

    public List<PersonShortDto> getPersonsOnlyDto() {
        return getPersons().stream().map(personShortMapper::toDto).collect(Collectors.toList());
    }

    public Person savePersonIfExists(ParticipantFilm participant){
        Person p = new Person();//УБРАТЬ
        p.setName(participant.getName());
        p.setSurname(participant.getSurname());
        return personRepository.findByNameAndSurname(
            participant.getName(), participant.getSurname()
        ).orElse(


            personRepository.save(p

            )
        );
    }
}
