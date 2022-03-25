package com.example.my_kinopoisk.domain.entities;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GenreCreateDto implements Serializable {
    @NotNull
    private final String title;
}
