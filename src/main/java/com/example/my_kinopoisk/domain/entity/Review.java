package com.example.my_kinopoisk.domain.entity;

import com.example.my_kinopoisk.validation.OnCreate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Null;
import java.time.LocalDate;
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

    @CreatedDate
    private LocalDate createDate;

    @LastModifiedDate
    private LocalDate updatedDate;
    private String title;
    private String body;
    private Float rating;

    @ManyToOne
    private Movie movie;

}
