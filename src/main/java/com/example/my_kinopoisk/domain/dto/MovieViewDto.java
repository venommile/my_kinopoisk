package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.OffsetDateTime;
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
    private final OffsetDateTime releaseDate;
    private final Set<GenreViewDto> genres;
    private final Set<ActorInMovieDto> actors;
    private final Set<FilmCrewMovieViewDto> filmCrews;


}
