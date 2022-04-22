package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.exception.MovieNotFoundException;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@RequiredArgsConstructor
@Service
public class MovieService {
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final GenreService genreService;
    private final ActorService actorService;
    private final FilmCrewService filmCrewService;
    private final GenreMapper genreMapper;

    public List<Movie> getMovies(String title, Pageable pageable) {//to ViewDto?
        return new ArrayList<>(movieRepository.findByTitleContainingIgnoreCase(title));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<MovieInListDto> getMovies(String title, List<GenreDto> genreDtoList, Pageable pageable) {//optimize?

        var foundMovies = getMovies(title, pageable);
        if(!genreDtoList.isEmpty()){
            List<Movie> moviesWithRequiredGenre = new ArrayList<>();
            for (var movie : foundMovies) {
                for (var foundGenre : movie.getGenres()) {
                    if (genreDtoList.contains(genreMapper.toDto(foundGenre))) {
                        moviesWithRequiredGenre.add(movie);
                    }
                }
            }
            foundMovies =  moviesWithRequiredGenre;
        }


        return foundMovies.stream().map(movieMapper::toInListDto).collect(Collectors.toList());

    }

    public List<Movie> getMovies(Pageable pageable) {
        return StreamSupport.stream(
            movieRepository.findAll(pageable).spliterator(),
            false).collect(Collectors.toList());
    }

    public MovieViewDto getMovieViewDto(Long id) {
        return movieMapper.toViewDto(
            getMovie(id)
        );
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow(MovieNotFoundException::new);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public MovieInListDto saveMovieDto(MovieInListDto movieInListDto) {

        return movieMapper.toInListDto(
            saveMovie(
                movieMapper.toEntity(movieInListDto)
            )
        );
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public MovieViewDto saveMovieDto(MovieCreateDto movieDto) {
        var movie = movieMapper.toEntity(movieDto);
        movie.setFilmCrews(
            filmCrewService.saveAndBindPerson(
                movie.getFilmCrews()
            )
        );
        movie.setActors(
            actorService.saveAndBindPerson(
                movie.getActors()
            )
        );
        movie.setGenres(
            genreService.saveAndBindGenres(
                movie.getGenres()
            )
        );
        movie.getGenres().forEach(genre -> genre.addMovie(movie));
        movie.getActors().forEach(actor -> actor.setMovie(movie));
        movie.getFilmCrews().forEach(crew -> crew.setMovie(movie));
        return movieMapper.toViewDto(
            saveMovie(movie)
        );
    }


    public void deleteMovie(Long id) {
        getMovie(id);
        movieRepository.deleteById(id);
    }


    public List<MovieInListDto> getMoviesInListDto(Pageable pageable) {
        return getMovies(pageable).stream().map(movieMapper::toInListDto).collect(Collectors.toList());
    }


}
