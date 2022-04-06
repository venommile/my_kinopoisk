package com.example.my_kinopoisk.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(uniqueConstraints =
@UniqueConstraint(name = "FullName", columnNames = {"name", "surname"}))
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    private String description;
    private Float height;
    private Date birthday;
    private Gender gender;

    public Person(ParticipantFilm participant){
        name = participant.getName();
        surname = participant.getSurname();
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Actor> actorRoles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private Set<FilmCrew> filmCrewRoles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    private Set<Genre> genres = new HashSet<>();



}
