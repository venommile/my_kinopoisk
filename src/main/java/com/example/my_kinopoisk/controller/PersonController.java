package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.service.PersonService;
import lombok.RequiredArgsConstructor;
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
    private final PersonService PersonService;

    @GetMapping("")
    public ResponseEntity<Iterable<PersonInListDto>> getPersons() {
        return ResponseEntity.ok(PersonService.getPersonsOnlyDto());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonViewDto> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(PersonService.getPerson(id));
    }

    @PostMapping("")
    public ResponseEntity<PersonViewDto> savePerson(@RequestBody PersonCreateDto personDto) {
        return ResponseEntity.ok(PersonService.savePerson(personDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        PersonService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}





