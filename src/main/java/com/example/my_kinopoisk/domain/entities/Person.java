package com.example.my_kinopoisk.domain.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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


    @OneToMany(fetch = FetchType.LAZY)

    private List<Actor> actorRoles = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)

    private List<FilmCrew> filmCrewRoles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "persons")
    private Set<Genre> genres = new HashSet<>();


}
