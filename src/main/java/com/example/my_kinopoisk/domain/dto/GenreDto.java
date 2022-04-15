package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Null;
import java.io.Serializable;


@Getter
@AllArgsConstructor
public class GenreDto implements Serializable {
    @Null(groups = OnCreate.class)
    private Long id;
    private String title;
}
