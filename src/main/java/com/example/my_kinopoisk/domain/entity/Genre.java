package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;


@Getter
@Setter
@Entity
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = OnCreate.class)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade =
        {
            CascadeType.DETACH,
            CascadeType.REFRESH
        }
    )
    private Set<Movie> movies = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Person> persons = new HashSet<>();

    public void addPerson(Person person) {
        persons.add(person);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id.equals(genre.id) && title.equals(genre.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }
}
