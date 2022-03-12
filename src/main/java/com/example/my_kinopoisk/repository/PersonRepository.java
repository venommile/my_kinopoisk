package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
}
