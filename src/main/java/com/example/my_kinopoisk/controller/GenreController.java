package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.GenreViewDto;
import com.example.my_kinopoisk.domain.dto.GenreCreateDto;
import com.example.my_kinopoisk.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<GenreViewDto> saveGenre(@RequestBody GenreCreateDto genreCreateDto) {
        return ResponseEntity.ok(genreService.saveGenreDto(genreCreateDto));
    }

    @GetMapping("")
    public ResponseEntity<Iterable<GenreViewDto>> getGenres() {
        return ResponseEntity.ok(genreService.getGenres());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id){
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }






}
