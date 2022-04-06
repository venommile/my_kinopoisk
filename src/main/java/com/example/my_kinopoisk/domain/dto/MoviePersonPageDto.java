package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MoviePersonPageDto implements Serializable {
    private final Long id;
    private final String title;
}
