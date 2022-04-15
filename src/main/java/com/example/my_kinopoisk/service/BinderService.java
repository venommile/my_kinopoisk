package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.entity.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BinderService {
    private final MovieService movieService;
    private final GenreService genreService;

    @Transactional(propagation = Propagation.REQUIRED)
    public Movie bindMovieGenre(Long movieId, Long genreId) {
        var movie = movieService.getMovie(movieId);
        var genre = genreService.getGenre(genreId);
        movie.addGenre(genre);
        return movieService.saveMovie(movie);
    }

}
