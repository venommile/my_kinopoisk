package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class MoviePersonPageDto implements Serializable {
    private final Long id;
    private final String title;
}
