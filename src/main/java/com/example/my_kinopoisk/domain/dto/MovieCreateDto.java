package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class MovieCreateDto implements Serializable {
    private final String title;
    @Min(0)
    private final Integer ageLimit;
    private final String countryOfProduction;
    private final String description;
    private final OffsetDateTime releaseDate;

    @Valid
    private final Set<GenreDto> genres;
    @Valid
    private final Set<ActorMovieViewDto> actors;
    @Valid
    private final Set<FilmCrewMovieViewDto> filmCrews;
    //   private final Set<RatingDto> ratings;
}
