package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class PersonInListDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
}
