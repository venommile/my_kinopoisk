package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class MovieInListDto implements Serializable {
    private Long id;
    private String title;
    private Integer ageLimit;
    private String countryOfProduction;
}
