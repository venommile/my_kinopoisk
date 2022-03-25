package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.dto.ShortMovieDto;
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
    private final MovieShortMapper movieShortMapper;
    private final MovieViewMapper movieViewMapper;
    private final MovieCreateMapper movieCreateMapper;

    public List<Movie> getMovies() {
        return StreamSupport.stream(
            movieRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public MovieViewDto getMovieViewDto(Long id) {
        var t =  movieRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
        return movieViewMapper.toDto(
           t
        );
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    public ShortMovieDto saveMovieDto(ShortMovieDto shortMovieDto) {
        return movieShortMapper.toDto(
            movieRepository.save(
                movieShortMapper.toEntity(shortMovieDto)
            )
        );
    }

    public MovieViewDto saveMovieDto(MovieCreateDto movieDto) {
        return movieViewMapper.toDto(
            movieRepository.save(
                movieCreateMapper.toEntity(movieDto)
            ));
    }


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<ShortMovieDto> getMoviesOnlyDto() {
        return getMovies().stream().map(movieShortMapper::toDto).collect(Collectors.toList());
    }


}
