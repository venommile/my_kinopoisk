package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.domain.entities.MoviePersonPageDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FilmCrewDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    private final String surname;
    private final String role;
    private final MoviePersonPageDto movie;
}
