package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ActorCreateDto implements Serializable {
    private final String name;
    private final String surname;
    private final String role;
}
