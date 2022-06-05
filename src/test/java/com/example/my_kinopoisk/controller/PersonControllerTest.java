package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.dto.PersonInListDto;
import com.example.my_kinopoisk.domain.entity.Person;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.mapper.PersonMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
public class PersonControllerTest extends MyKinopoiskApplicationTests {


    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    public Person getPersonWithSomeData() {
        var person = new Person();
        person.setName("name");
        person.setSurname("surname");
        person.setBirthday(Date.valueOf(LocalDate.parse("1984-10-26")));
        person.setDescription("descr");
        person.setHeight(1.8f);
        return person;
    }

    public Person savePersonWithSomeData() {
        return personRepository.save(getPersonWithSomeData());
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Transactional
    @Test
    void getPersonAdmin() throws Exception {

        var person = savePersonWithSomeData();

        var expectedResponse = objectMapper.writeValueAsString(person);

        mockMvc.perform(get("/persons/" + person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMovieNotFoundAdmin() throws Exception {
        mockMvc.perform(get("/persons/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value("NOT_FOUND"))
            .andExpect(jsonPath("$.detail").value(String.format("Person '%s' not found", 1)));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getPersonsFoundAdmin() throws Exception {
        var person = savePersonWithSomeData();

        var personsList = new ArrayList<PersonInListDto>();
        personsList.add(personMapper.toPersonInListDto(person));

        var personAns = new PageImpl<>(personsList);

        var expectedResponse = objectMapper.writeValueAsString(personAns);

        mockMvc.perform(get("/persons/"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void deletePersonSuccessAdmin() throws Exception {
        var person = savePersonWithSomeData();

        Assertions.assertEquals(1L, personRepository.count());


        mockMvc.perform(delete("/persons/" + person.getId()))
            .andExpect(status().isNoContent());

        Assertions.assertEquals(0L, personRepository.count());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void savePersonAdmin() throws Exception {
        var person = getPersonWithSomeData();

        Assertions.assertEquals(personRepository.count(), 0L);


        var requestContent = objectMapper.writeValueAsString(person);

        mockMvc.perform(post("/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().is(200));

        Assertions.assertEquals(personRepository.count(), 1L);
    }

}
