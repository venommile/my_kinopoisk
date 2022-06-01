package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Getter
@Setter
@Entity
public class FilmCrew implements ParticipantFilm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = OnCreate.class)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Movie movie;

    @Column(name = "person_id", insertable = false, updatable = false)
    private Long personId;

}
