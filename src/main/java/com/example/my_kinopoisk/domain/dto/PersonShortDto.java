package com.example.my_kinopoisk.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PersonShortDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
}
