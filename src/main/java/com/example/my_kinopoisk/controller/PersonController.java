package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.domain.entities.Actor;
import com.example.my_kinopoisk.domain.entities.Person;
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

    public ResponseEntity<Iterable<Person>> getPersons() {
        return ResponseEntity.ok(PersonService.getPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return ResponseEntity.ok(PersonService.getPerson(id));
    }

    @PostMapping("")
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        return ResponseEntity.ok(PersonService.savePerson(person));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        PersonService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}





