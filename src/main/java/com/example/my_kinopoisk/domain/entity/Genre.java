package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = OnCreate.class)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();


    @ManyToMany(fetch = FetchType.LAZY)
    private List<Person> persons = new ArrayList<>();

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

}
