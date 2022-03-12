package com.example.my_kinopoisk.service;

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

    public Person getPerson(Long id) {
        return personRepository.findById(id).orElseThrow();
    }
}
