package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entities.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long > {
}
