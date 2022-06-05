package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.service.PersonService;
import com.example.my_kinopoisk.validation.OnSearch;
import com.example.my_kinopoisk.wrapper.MovieSearchWrapper;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final MovieService movieService;
    private final PersonService personService;

    @PostMapping("/movie")
    @PreAuthorize("hasAuthority('read')")
    @Validated(OnSearch.class)
    public Page<MovieInListDto> searchMovie(@Valid @RequestBody MovieSearchWrapper movieSearchWrapper, @ParameterObject Pageable pageable) {
        return new PageImpl<>(movieService.get(movieSearchWrapper.getTitle(),
            movieSearchWrapper.getValidatedList(),
            pageable)
        );

    }


    @GetMapping("/person/{searchRequest}")
    @PreAuthorize("hasAuthority('read')")
    public Page<PersonInListDto> searchPerson(@PathVariable String searchRequest,
                                              @ParameterObject Pageable pageable) {
        return new PageImpl<>(personService.search(searchRequest, pageable));
    }
}
