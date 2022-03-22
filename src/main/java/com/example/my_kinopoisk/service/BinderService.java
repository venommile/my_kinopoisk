package com.example.my_kinopoisk.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BinderService {
    private final MovieService movieService;
    private final GenreService genreService;

    public Movie bindMovieGenre(Long movieId, Long genreId){
        var movie = movieService.getMovie(movieId);
        var genre = genreService.getGenre(genreId);
        movie.addGenre(genre);
        return movieService.saveMovie(movie);
    }

}
