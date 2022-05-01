package com.example.my_kinopoisk.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RatingDto implements Serializable {
    private final Long id;
    @NotNull
    private final String title;
    private final String description;
    private final Float score;
    private final Integer countScores;
}
