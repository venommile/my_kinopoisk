package com.example.my_kinopoisk.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MovieCreateDto implements Serializable {
    private final String title;
    @Min(0)
    private final Integer ageLimit;
    private final String countryOfProduction;
    private final String description;
    private final LocalDate releaseDate;

    @Valid
    private final Set<GenreDto> genres;
    @Valid
    private final Set<ActorMovieViewDto> actors;
    @Valid
    private final Set<FilmCrewMovieViewDto> filmCrews;
    //   private final Set<RatingDto> ratings;
}
