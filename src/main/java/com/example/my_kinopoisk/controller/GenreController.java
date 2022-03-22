package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.dto.MovieDto;
import com.example.my_kinopoisk.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;

    @PostMapping("")
    public ResponseEntity<GenreDto> saveGenre(@RequestBody GenreDto genreDto) {
        return ResponseEntity.ok(genreService.saveGenreDto(genreDto));
    }

//    @GetMapping("")
//    public ResponseEntity<Iterable<GenreDto>> getGenres() {
//        return ResponseEntity.ok(genreService.getGenres());
//    }



}
