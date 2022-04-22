package com.example.my_kinopoisk.domain.entity;


import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = OnCreate.class)
    private Long id;
    private String title;
    @Min(0)
    private Integer ageLimit;
    private String countryOfProduction;
    @Column(length = 1000)
    private String description;


    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Genre> genres = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Set<Actor> actors = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_id")
    private Set<FilmCrew> filmCrews = new HashSet<>();


//    @ManyToMany(fetch = FetchType.LAZY)
//
//    private Set<Rating> ratings = new HashSet<>();


    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

//    public void addRating(Rating rating) {
//        ratings.add(rating);
//    }

    public void addFilmCrew(FilmCrew filmCrew) {
        filmCrews.add(filmCrew);
    }


    // to Servece





    /* List of  Poster URL's */

}
