package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByTitle(String title);
}
