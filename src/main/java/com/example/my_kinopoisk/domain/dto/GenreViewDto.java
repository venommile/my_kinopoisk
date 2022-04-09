package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public class GenreViewDto implements Serializable {
    private Long id;
    private String title;
}
