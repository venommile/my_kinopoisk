package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Person;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {


    @EntityGraph(attributePaths = {"actorRoles", "filmCrewRoles", "genres"})
    Optional<Person> findByNameAndSurname(String name, String surname);


    @Override
    @EntityGraph(attributePaths = {"actorRoles", "filmCrewRoles", "genres"})
    Optional<Person> findById(Long id);

}
