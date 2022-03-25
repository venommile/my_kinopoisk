package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieShortDto implements Serializable {
    private Long id;
    private String title;
    private Integer ageLimit;
    private String countryOfProduction;
    private String description;
}
