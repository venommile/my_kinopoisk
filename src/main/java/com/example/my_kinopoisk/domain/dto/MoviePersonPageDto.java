package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class MoviePersonPageDto implements Serializable {
    private final Long id;
    private final String title;
}
