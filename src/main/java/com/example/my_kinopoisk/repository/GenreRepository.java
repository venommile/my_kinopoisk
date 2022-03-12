package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entities.Genre;
import org.springframework.data.repository.CrudRepository;




public interface GenreRepository extends CrudRepository<Genre, Long> {
}
