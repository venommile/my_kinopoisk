package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.domain.entity.Movie_;
import com.example.my_kinopoisk.problem.MovieNotFoundProblem;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
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


    public List<MovieInListDto> getMovies(String title, List<GenreDto> genreDtoList, Pageable pageable) {

        var foundMovies = movieRepository.findAll(
            Specification.where(titleContainingIgnoreCase(title).and(containsGenre(genreDtoList))), pageable);

        return foundMovies.stream().map(movieMapper::toInListDto).collect(Collectors.toList());

    }

    public Specification<Movie> containsGenre(List<GenreDto> genreDtoList) {
        if (genreDtoList == null || genreDtoList.isEmpty()) {
            return null;
        }

        var genres = genreDtoList.stream().map(genreMapper::toEntity).collect(Collectors.toList());
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (var genre : genres) {
                predicates.add(cb.isMember(genre, root.get(Movie_.genres)));
            }
            return cb.or(predicates.toArray(new Predicate[]{}));

        };

    }

    public Specification<Movie> titleContainingIgnoreCase(String title) {
        if (title == null || title.isBlank()) return null;

        return (root, query, criteriaBuilder) ->
            criteriaBuilder.like(
                criteriaBuilder.upper(root.get(Movie_.title)), "%" + title.toUpperCase() + "%"
            );


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
        return movieRepository.findById(id).orElseThrow(() -> new MovieNotFoundProblem(id));
    }

    @Transactional
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


    @Transactional
    public MovieInListDto saveMovieDto(MovieInListDto movieInListDto) {

        return movieMapper.toInListDto(
            saveMovie(
                movieMapper.toEntity(movieInListDto)
            )
        );
    }

    @Transactional
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


    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }


    public List<MovieInListDto> getMoviesInListDto(Pageable pageable) {
        return getMovies(pageable).stream().map(movieMapper::toInListDto).collect(Collectors.toList());
    }


}
