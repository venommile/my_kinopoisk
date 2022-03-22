package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.ShortMovieDto;
import com.example.my_kinopoisk.domain.entities.Movie;
import com.example.my_kinopoisk.service.BinderService;
import com.example.my_kinopoisk.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final BinderService binderService;
    @GetMapping("")
    public ResponseEntity<Iterable<ShortMovieDto>> getMovies() {
        return ResponseEntity.ok(movieService.getMoviesOnlyDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovie(id));
    }

    @PostMapping("")
    public ResponseEntity<ShortMovieDto> saveMovie(@RequestBody ShortMovieDto shortMovieDto) {
        return ResponseEntity.ok(movieService.saveMovieDto(shortMovieDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{movieId}/genre/{genreId}")
    public Movie genreToMovie(@PathVariable Long movieId,
                              @PathVariable Long genreId) {
        return binderService.bindMovieGenre(movieId,genreId);
    }



//    @PutMapping("/{movieId}/actor/{actorId}")
//    public Movie actorToMovie(@PathVariable Long movieId,
//                              @PathVariable Long actorId){
//        var movie = movieService.getMovie(movieId);
//
//    }

}
