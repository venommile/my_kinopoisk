package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class FullMovieDto implements Serializable {
    private final Long id;
    private final String title;
    @Min(0)
    private final Integer ageLimit;
    private final String countryOfProduction;
    private final String description;
    private final OffsetDateTime releaseDate;
    private final Set<GenreDto> genres;
    private final Set<ActorDto> actors;
    private final Set<FilmCrewWorkerDto> filmCrews;
    private final Set<RatingDto> ratings;
}
