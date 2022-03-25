package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.domain.entities.Gender;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
public class PersonCreateDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
    private final String description;
    private final Float height;
    private final Date birthday;
    private final Gender gender;
}
