package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = OnCreate.class)
    private Long id;

    @NotNull
    private String title;
    private String description;
    private Float score;
    private Integer countScores;


//    @ManyToMany(mappedBy = "ratings", fetch = FetchType.LAZY)
//    private List<Movie> movies = new ArrayList<>();

    public String getTitle() {
        return title;
    }

//    public void addMovie(Movie movie) {
//        movies.add(movie);
//    }

}
