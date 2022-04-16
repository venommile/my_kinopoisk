package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
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
import javax.validation.constraints.Null;
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
    @Null(groups = OnCreate.class)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String surname;

    @Column(length = 1000)
    private String description;
    private Float height;
    private Date birthday;
    private Gender gender;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Actor> actorRoles = new HashSet<>();
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private Set<FilmCrew> filmCrewRoles = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    private Set<Genre> genres = new HashSet<>();

    public Person(ParticipantFilm participant) {
        name = participant.getName();
        surname = participant.getSurname();
    }


}
