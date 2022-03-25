package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.entities.Actor;
import com.example.my_kinopoisk.domain.entities.FilmCrew;
import com.example.my_kinopoisk.repository.FilmCrewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmCrewService {
    private final FilmCrewRepository filmCrewRepository;
    private final PersonService personService;
    public Set<FilmCrew> saveAndBindPerson(Set<FilmCrew> filmCrews) {
        Set<FilmCrew> saveFilmCrews = new HashSet<>();
        for (var filmCrew : filmCrews) {
            filmCrew.setPerson( personService.savePersonIfExists(filmCrew));
            saveFilmCrews.add(filmCrewRepository.save(filmCrew));
        }
        return saveFilmCrews;
    }
}
