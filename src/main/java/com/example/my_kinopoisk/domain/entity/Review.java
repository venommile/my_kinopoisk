package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Null;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
public class Review {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Null(groups = OnCreate.class)
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
