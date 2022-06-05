package com.example.my_kinopoisk.repository;


import com.example.my_kinopoisk.domain.entity.FilmCrew;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmCrewRepository extends JpaRepository<FilmCrew, Long> {
}
