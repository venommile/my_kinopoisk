package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.domain.entity.Gender;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

@Data
public class PersonCreateDto implements Serializable {
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
    private final String description;
    private final Float height;
    private final Date birthday;
    private final Gender gender;
}
