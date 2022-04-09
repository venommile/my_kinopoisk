package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class FilmCrewDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    private final String surname;
    private final String role;
    private final MoviePersonPageDto movie;
}
