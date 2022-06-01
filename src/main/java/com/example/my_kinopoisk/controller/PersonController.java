package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.service.PersonService;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    @GetMapping
    @PreAuthorize("hasAuthority('read')")
    public Page<PersonInListDto> getPersons(@ParameterObject Pageable pageable) {
        return new PageImpl<>(personService.getPersonsOnlyDto(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('read')")
    public ResponseEntity<PersonViewDto> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonDto(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('write')")
    @Validated(OnCreate.class)
    public ResponseEntity<PersonViewDto> savePerson(@Valid @RequestBody PersonCreateDto personDto) {
        return ResponseEntity.ok(personService.savePerson(personDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}





