package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.domain.dto.GenreDto;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.service.PersonService;
import com.example.my_kinopoisk.validation.ValidatedList;
import com.example.my_kinopoisk.wrapper.MovieSearchWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final MovieService movieService;
    private final PersonService personService;
    private final ObjectMapper objectMapper;

    @PostMapping("/movie")
    @PreAuthorize("hasAuthority('read')")
    public Page<MovieInListDto> searchMovie(@RequestBody MovieSearchWrapper movieSearchWrapper, @ParameterObject Pageable pageable) {
        return new PageImpl<>(movieService.getMovies(movieSearchWrapper.getTitle(),
            movieSearchWrapper.getValidatedList(),
            pageable)
        );

    }


    @GetMapping("/person/searchRequest")
    @PreAuthorize("hasAuthority('read')")
    public Page<PersonInListDto> searchPerson(String searchRequest,
                                              @ParameterObject Pageable pageable) {
        return new PageImpl<>(personService.search(searchRequest, pageable));
    }
}
