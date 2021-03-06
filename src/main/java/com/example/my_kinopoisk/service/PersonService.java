package com.example.my_kinopoisk.service;

import com.example.my_kinopoisk.domain.dto.PersonCreateDto;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.dto.PersonViewDto;
import com.example.my_kinopoisk.domain.entity.ParticipantFilm;
import com.example.my_kinopoisk.domain.entity.Person;
import com.example.my_kinopoisk.domain.entity.Person_;
import com.example.my_kinopoisk.problem.PersonNotFoundProblem;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public List<Person> getDto(Pageable pageable) {
        return StreamSupport.stream(
            personRepository
                .findAll(pageable)
                .spliterator(),
            false).collect(Collectors.toList());
    }

    public void delete(Long id) {
        personRepository.deleteById(id);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public PersonViewDto save(PersonCreateDto personDto) {
        var foundPerson = personRepository.findByNameAndSurname(personDto.getName(), personDto.getSurname());
        var person = personMapper.toEntity(personDto);
        person.setId(foundPerson.map(Person::getId).orElse(null));
        return personMapper.toViewDto(
            personRepository.save(person)
        );
    }

    public PersonViewDto getDto(Long id) {
        return personMapper.toViewDto(
            get(id)
        );
    }

    public Person get(Long id) {
        return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundProblem(id));
    }

    public List<PersonInListDto> getPersonsOnlyDto(Pageable pageable) {
        return getDto(pageable).stream().map(personMapper::toPersonInListDto).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Person savePersonIfExists(ParticipantFilm participant) {
        return personRepository.findByNameAndSurname(participant.getName(), participant.getSurname())
            .orElseGet(
                () -> personRepository.save(
                    new Person(participant)
                )
            );
    }

    private Page<Person> searchPerson(String searchRequest, Pageable pageable) {
        return personRepository.findAll(Specification.where(
            searchFirstName(searchRequest)).or(searchLastName(searchRequest)), pageable);
    }

    public List<PersonInListDto> search(String searchRequest, Pageable pageable) {
        return searchPerson(searchRequest, pageable).map(personMapper::toPersonInListDto).stream().collect(Collectors.toList());
    }


    private Specification<Person> searchLastName(String searchRequest) {
        var words = searchRequest.split("\\s+", 2);
        String surname = null;
        if (words.length > 1) {
            surname = words[1];
        } else if (words.length > 0) {
            surname = words[0];
        }

        if (surname != null && surname.length() > 0) {
            String finalSurname = surname;
            return (root, query, cb) -> cb.like(cb.upper(root.get(Person_.surname)), "%" + finalSurname.toUpperCase() + "%");
        }
        return null;
    }

    private Specification<Person> searchFirstName(String searchRequest) {
        var words = searchRequest.split("\\s+", 2);
        String name = null;
        if (words.length > 0) {
            name = words[0];
        }
        System.out.println(name);
        if (name != null && name.length() > 0) {
            String finalName = name;
            return (root, query, cb) -> cb.like(cb.upper(root.get(Person_.name)), "%" + finalName.toUpperCase() + "%");
        }
        return null;
    }
}
