package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/persons")
public class PersonController {
    private final PersonService personService;

    @GetMapping("")
    public Page<PersonInListDto> getPersons(@ParameterObject Pageable pageable) {
        return new PageImpl<>(personService.getPersonsOnlyDto(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonViewDto> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(personService.getPersonDto(id));
    }

    @PostMapping("")
    public ResponseEntity<PersonViewDto> savePerson(@RequestBody PersonCreateDto personDto) {
        return ResponseEntity.ok(personService.savePerson(personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}





