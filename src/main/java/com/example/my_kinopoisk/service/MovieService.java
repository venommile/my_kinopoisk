package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
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
    private final MovieInListMapper movieInListMapper;
    private final MovieViewMapper movieViewMapper;
    private final MovieCreateMapper movieCreateMapper;
    private final GenreService genreService;
    private final ActorService actorService;
    private final FilmCrewService filmCrewService;

    public List<Movie> getMovies() {
        return StreamSupport.stream(
            movieRepository.findAll().spliterator(),
            false).collect(Collectors.toList());
    }

    public MovieViewDto getMovieViewDto(Long id) {
        return movieViewMapper.toDto(
            movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie with this id not found"))
        );
    }

    public Movie getMovie(Long id) {
        return movieRepository.findById(id).orElseThrow();
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    public MovieInListDto saveMovieDto(MovieInListDto movieInListDto) {

        return movieInListMapper.toDto(
            movieRepository.save(
                movieInListMapper.toEntity(movieInListDto)
            )
        );
    }

    public MovieViewDto saveMovieDto(MovieCreateDto movieDto) {
        var movie = movieCreateMapper.toEntity(movieDto);
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
        movie.getGenres().forEach(genre -> genre.addMovie(movie));//Ужасно((
        movie.getActors().forEach(actor -> actor.setMovie(movie));
        movie.getFilmCrews().forEach(crew -> crew.setMovie(movie));
        return movieViewMapper.toDto(
            movieRepository.save(movie)
        );
    }


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<MovieInListDto> getMoviesOnlyDto() {
        return getMovies().stream().map(movieInListMapper::toDto).collect(Collectors.toList());
    }


}
