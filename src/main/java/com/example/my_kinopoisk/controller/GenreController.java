package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.service.GenreService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/genre")
public class GenreController {
    private final GenreService genreService;


    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    @Validated(OnCreate.class)
    public ResponseEntity<Genre> saveGenre(@Valid @RequestBody Genre genre) {
        return ResponseEntity.ok(genreService.saveGenre(genre));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public Page<GenreDto> getGenres(@ParameterObject Pageable pageable) {
        return new PageImpl<>(genreService.getGenresListDto(pageable));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

}
