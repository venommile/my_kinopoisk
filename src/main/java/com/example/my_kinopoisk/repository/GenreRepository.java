package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByTitle(String title);
}
