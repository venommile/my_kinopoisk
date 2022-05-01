package com.example.my_kinopoisk.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;


@AllArgsConstructor
@Getter
public class ReviewViewDto {
    private Long id;

    private String userName;
    private OffsetDateTime createDate;

    private OffsetDateTime updatedDate;


    private String title;
    private String body;

    private Float rating;

}
