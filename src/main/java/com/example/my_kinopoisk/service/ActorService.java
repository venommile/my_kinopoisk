package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.entity.Actor;
import com.example.my_kinopoisk.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final PersonService personService;


    public Set<Actor> saveAndBindPerson(Set<Actor> actors) {
        Set<Actor> saveActors = new HashSet<>();
        if (actors != null) {
            for (var actor : actors) {
                actor.setPerson(personService.savePersonIfExists(actor));
                saveActors.add(actorRepository.save(actor));
            }
        }
        return saveActors;
    }
}
