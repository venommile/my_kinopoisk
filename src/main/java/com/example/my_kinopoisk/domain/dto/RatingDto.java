package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class RatingDto implements Serializable {
    private final Long id;
    @NotNull
    private final String title;
    private final String description;
    private final Float score;
    private final Integer countScores;
}
