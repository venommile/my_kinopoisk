package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}
