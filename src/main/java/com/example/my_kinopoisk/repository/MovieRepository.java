package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}

