package com.example.my_kinopoisk.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Actor implements ParticipantFilm{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;



    @NotNull
    private String name;
    private String surname;
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    @ManyToOne()
    private Person person;

}
