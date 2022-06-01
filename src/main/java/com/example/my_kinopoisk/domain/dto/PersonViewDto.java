package com.example.my_kinopoisk.domain.dto;

import com.example.my_kinopoisk.domain.entity.Gender;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PersonViewDto implements Serializable {
    private final Long id;
    @NotNull
    private final String name;
    @NotNull
    private final String surname;
    private final String description;
    private final Float height;
    private final LocalDate birthday;
    private final Gender gender;
    private final Set<ActorInPersonDto> actorRoles;
    private final Set<FilmCrewInPerson> filmCrewRoles;
    private final Set<GenreDto> genres;
}
