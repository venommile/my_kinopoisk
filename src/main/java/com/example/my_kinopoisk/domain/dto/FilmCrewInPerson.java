package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class FilmCrewInPerson implements Serializable {
    private final Long id;
    private final String role;
    private final MoviePersonPageDto movie;
}
