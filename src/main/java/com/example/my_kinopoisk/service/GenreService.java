package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.GenreViewDto;
import com.example.my_kinopoisk.domain.entities.Genre;
import com.example.my_kinopoisk.domain.entities.GenreCreateDto;
import com.example.my_kinopoisk.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
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
    private final GenreCreateMapper genreCreateMapper;

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(Long id) {
        return genreRepository.findById(id).orElseThrow();
    }


    public GenreViewDto saveGenreDto(GenreCreateDto genreCreateDto) {
        //System.out.println(genreMapper.toEntity(genreViewDto).getTitle());
        return genreMapper.toDto(genreRepository.save(genreCreateMapper.toEntity(genreCreateDto)));
    }

    public List<Genre> getGenres() {
        return StreamSupport.stream(
            genreRepository
                .findAll()
                .spliterator(), false).collect(Collectors.toList());
    }


    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }


    public Set<Genre> saveAndBindGenres(Set<Genre> genres) {
        Set<Genre> saveGenres = new HashSet<>();
        for (var genre : genres) {
            var foundGenre = genreRepository.findByTitle(
                genre.getTitle());
            if (foundGenre.isEmpty()){
                saveGenres.add(genreRepository.save(genre));
            }
            else{
                saveGenres.add(foundGenre.get());
            }
        }
        return saveGenres;
    }
}
