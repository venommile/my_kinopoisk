package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final PersonService PersonService;
    private final MovieService movieService;

}
