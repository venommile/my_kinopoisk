package com.example.my_kinopoisk.domain.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    @Min(0)
    private Integer ageLimit;
    private String countryOfProduction;
    @Column(length = 1000)
    private String description;


    private OffsetDateTime releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Genre> genres = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY)
    private Set<Actor> actors = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY)
    private Set<FilmCrewWorker> filmCrews = new HashSet<>();


    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();


    public void addGenre(Genre genre) {
        genres.add(genre);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void addFilmCrew(FilmCrewWorker filmCrew) {
        filmCrews.add(filmCrew);
    }


    // to Servece







    /* List of  Poster URL's */

}
