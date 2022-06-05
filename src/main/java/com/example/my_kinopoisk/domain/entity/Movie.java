package com.example.my_kinopoisk.domain.entity;


import com.example.my_kinopoisk.validation.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Null(groups = OnCreate.class)
    private Long id;
    private String title;
    @Min(0)
    private Integer ageLimit;
    private String countryOfProduction;
    @Column(length = 1000)
    private String description;


    private LocalDate releaseDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade =
        {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
        }
    )
    private Set<Genre> genres = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REMOVE
    })
    @JoinColumn(name = "movie_id")
    private Set<Actor> actors = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE,
        CascadeType.PERSIST,
        CascadeType.REMOVE
    })
    @JoinColumn(name = "movie_id")
    private Set<FilmCrew> filmCrews = new HashSet<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "movie_id")
    private Set<Review> reviews = new HashSet<>();

    public void addGenre(Genre genre) {
        if (!checkIfSetMutable(genres)) genres = new HashSet<>(genres);
        System.out.println(genre.getTitle() + genre.getId());
        genres.add(genre);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void addFilmCrew(FilmCrew filmCrew) {
        filmCrews.add(filmCrew);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public boolean checkIfSetMutable(Set set) {
        return set instanceof HashSet;
    }

}
