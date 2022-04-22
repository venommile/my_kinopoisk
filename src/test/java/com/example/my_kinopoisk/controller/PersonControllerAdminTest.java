package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"read", "write"})
public class PersonControllerAdminTest extends MyKinopoiskApplicationTests {

    String personNotFound = "Person was not found";
    ErrorResponse personNotFoundError;
    String personNotFoundMessage;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    void initMessages() throws JsonProcessingException {
        personNotFoundError = new ErrorResponse(personNotFound);

        personNotFoundMessage = objectMapper.writeValueAsString(personNotFoundError);
    }


    @AfterEach
    public void clean() {
        cleanTables();
    }


}
