package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    List<Movie> findByTitleContainingIgnoreCase(String title);

    List<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = {"reviews", "filmCrews", "genres", "actors"})
    Optional<Movie> findById(Long id);
}

