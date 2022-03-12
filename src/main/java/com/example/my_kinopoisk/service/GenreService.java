package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.entities.Genre;

import com.example.my_kinopoisk.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;
    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }
    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow();
    }


    public GenreDto saveGenreDto(GenreDto genreDto) {
        System.out.println(genreMapper.toEntity(genreDto).getTitle());
        return genreMapper.toDto(genreRepository.save(genreMapper.toEntity(genreDto)));
    }
}
