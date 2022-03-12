package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import com.example.my_kinopoisk.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public List<Movie> getMovies() {
        return StreamSupport.stream(
            movieRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    public MovieDto saveMovieDto(MovieDto movieDto) {
        return movieMapper.toDto(
            movieRepository.save(
                movieMapper.toEntity(movieDto)
            )
        );
    }


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<MovieDto> getMoviesOnlyDto() {
        return getMovies().stream().map(movieMapper::toDto).collect(Collectors.toList());
    }


}
