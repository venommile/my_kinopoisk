package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data

public class FilmCrewMovieViewDto {

    private final Long id;
    @NotNull
    private final String name;
    private final String surname;
    private final String role;
}
