package com.example.my_kinopoisk.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String username;

    private OffsetDateTime publicDate;
    private String title;
    private String body;
    private Float rating;
    @ManyToOne
    private Movie movie;

}
