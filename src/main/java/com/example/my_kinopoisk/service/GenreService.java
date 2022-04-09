package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreCreateDto;
import com.example.my_kinopoisk.domain.dto.GenreViewDto;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.exception.GenreNotFoundException;
import com.example.my_kinopoisk.repository.GenreRepository;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow(GenreNotFoundException::new);
    }


    public GenreViewDto saveGenreDto(GenreCreateDto genreCreateDto) {
        return genreMapper.toViewDto(saveGenre(genreMapper.toEntity(genreCreateDto)));
    }

    public List<GenreViewDto> getGenresListDto(Pageable pageable) {
        return StreamSupport.stream(
            genreRepository
                .findAll(pageable)
                .spliterator(), false).map(genreMapper::toViewDto).collect(Collectors.toList());
    }


    public void deleteGenre(Long id) {
        getGenre(id);
        genreRepository.deleteById(id);
    }


    public Set<Genre> saveAndBindGenres(Set<Genre> genres) {
        Set<Genre> saveGenres = new HashSet<>();
        for (var genre : genres) {
            var foundGenre = genreRepository.findByTitle(
                genre.getTitle());
            saveGenres.add(foundGenre.orElseGet(() -> genreRepository.save(genre)));
        }
        return saveGenres;
    }
}
