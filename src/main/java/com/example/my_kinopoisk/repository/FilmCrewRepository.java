package com.example.my_kinopoisk.repository;


import com.example.my_kinopoisk.domain.entity.FilmCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilmCrewRepository extends JpaRepository<FilmCrew, Long> {
}
