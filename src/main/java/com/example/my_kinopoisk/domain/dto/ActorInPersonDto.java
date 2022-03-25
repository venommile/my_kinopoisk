package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.domain.entities.MoviePersonPageDto;
import lombok.Data;

import java.io.Serializable;

@Data
public class ActorInPersonDto implements Serializable {
    private final Long id;
    private final String role;
    private final MoviePersonPageDto movie;
}