package com.example.my_kinopoisk.wrapper;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.validation.ValidatedList;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieSearchWrapper {
    private String title;
    private ValidatedList<GenreDto> validatedList;
}
