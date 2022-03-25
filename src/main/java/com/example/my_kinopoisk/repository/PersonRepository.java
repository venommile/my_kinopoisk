package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByNameAndSurname(String name, String surname);
}
