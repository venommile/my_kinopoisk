package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
public class SearchControllerTest extends MyKinopoiskApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @Autowired
    private MovieRepository movieRepository;

//    @WithMockUser(username = "admin", authorities = {"read", "write"})
//    @Test
//    @Transactional
//    public void searchMovie() throws Exception {
//        var movieId = 1000L;
//        var movie = new Movie();
//        movie.setId(movieId);
//        movie.setTitle("The Terminator");
//        movie.setAgeLimit(16);
//        movie.setCountryOfProduction("Country");
//        movie.setDescription("desc");
//        movie.setReleaseDate(LocalDate.parse("1984-10-26"));
//
//        movieRepository.save(movie);
//        var expectedResponse = objectMapper.writeValueAsString(movie);
//
//        mockMvc.perform(get("/movies/" + 1000))
//            .andExpect(status().isOk())
//            .andExpect(content().string(expectedResponse));
//
//    }
}
