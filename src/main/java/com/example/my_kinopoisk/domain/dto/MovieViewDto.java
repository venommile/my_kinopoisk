package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@Getter
public class MovieViewDto implements Serializable {
    private final Long id;
    private final String title;
    @Min(0)
    private final Integer ageLimit;
    private final String countryOfProduction;
    private final String description;
    private final LocalDate releaseDate;
    private final Set<GenreDto> genres;
    private final Set<ActorMovieViewDto> actors;
    private final Set<FilmCrewMovieViewDto> filmCrews;
}
