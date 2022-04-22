package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.MovieCreateDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.MovieViewDto;
import com.example.my_kinopoisk.service.BinderService;
import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final BinderService binderService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('read')")
    public Page<MovieInListDto> getMovies(@ParameterObject Pageable pageable) {
        return new PageImpl<>(movieService.getMoviesInListDto(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<MovieViewDto> getMovie(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getMovieViewDto(id));
    }

    @PostMapping("")
    @PreAuthorize("hasAuthority('write')")
    @Validated(OnCreate.class)
    public ResponseEntity<MovieViewDto> saveMovie(@Valid @RequestBody MovieCreateDto movieDto) {
        return ResponseEntity.ok(movieService.saveMovieDto(movieDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{movieId}/genre/{genreId}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<MovieViewDto> genreToMovie(@PathVariable Long movieId,
                                                     @PathVariable Long genreId) {
        return ResponseEntity.ok(binderService.bindMovieGenre(movieId, genreId));
    }


//    @PutMapping("/{movieId}/actor/{actorId}")
//    public Movie actorToMovie(@PathVariable Long movieId,
//                              @PathVariable Long actorId){
//        var movie = movieService.getMovie(movieId);
//
//    }

}
