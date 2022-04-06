package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Movie> getMovies() {
        return StreamSupport.stream(
                movieRepository.findAll().spliterator(),
                false).collect(Collectors.toList());
    }

    public MovieViewDto getMovieViewDto(Long id) {
        return movieMapper.toViewDto(
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

        return movieMapper.toInListDto(
                movieRepository.save(
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
        movie.getGenres().forEach(genre -> genre.addMovie(movie));//Ужасно((
        movie.getActors().forEach(actor -> actor.setMovie(movie));
        movie.getFilmCrews().forEach(crew -> crew.setMovie(movie));
        return movieMapper.toViewDto(
                movieRepository.save(movie)
        );
    }


    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<MovieInListDto> getMoviesOnlyDto() {
        return getMovies().stream().map(movieMapper::toInListDto).collect(Collectors.toList());
    }


}
