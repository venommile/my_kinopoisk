package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {
    private final MovieService movieService;
    private final PersonService personService;
    @GetMapping("/movie/{title}")
    @PreAuthorize("hasAuthority('read')")
    public Page<MovieInListDto> searchMovie(@PathVariable String title, @ParameterObject Pageable pageable) {
        return new PageImpl<>(movieService.getMovies(title, pageable));
    }


    @GetMapping("/person/{firstName}/{surname}")
    @PreAuthorize("hasAuthority('read')")
    public Page<PersonInListDto> searchPerson(@PathVariable(required = false) String firstName,
                                              @PathVariable(required = false) String surname,
                                              @ParameterObject Pageable pageable) {
        return new PageImpl<>(personService.search(firstName, surname,pageable));
    }
}
