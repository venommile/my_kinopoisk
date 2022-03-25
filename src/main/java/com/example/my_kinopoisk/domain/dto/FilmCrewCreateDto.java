package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class FilmCrewCreateDto implements Serializable {
    @NotNull
    private final String name;
    private final String surname;
    private final String role;
}
