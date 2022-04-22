package com.example.my_kinopoisk.wrapper;

import com.example.my_kinopoisk.domain.dto.GenreDto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;

@Getter
@Setter
public class MovieSearchWrapper {
    private String title;
    @Valid
    private ArrayList<GenreDto> validatedList;
}
