package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.entity.Actor;
import com.example.my_kinopoisk.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ActorService {
    private final ActorRepository actorRepository;
    private final PersonService personService;


    public Set<Actor> saveAndBindPerson(Set<Actor> actors) {
        Set<Actor> saveActors = new HashSet<>();
        for (var actor : actors) {
            actor.setPerson(personService.savePersonIfExists(actor));
            saveActors.add(actorRepository.save(actor));
        }
        return saveActors;
    }
}
