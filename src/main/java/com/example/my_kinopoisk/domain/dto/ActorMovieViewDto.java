package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ActorMovieViewDto implements Serializable {
    @Null(groups = OnCreate.class)
    private final Long id;
    @NotNull
    private final String name;
    private final String surname;
    private final String role;

    private final Long personId;
}
