package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.domain.entity.Person;
import com.example.my_kinopoisk.repository.GenreRepository;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.MovieService;
import com.example.my_kinopoisk.service.PersonService;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import com.example.my_kinopoisk.service.mapper.PersonMapper;
import com.example.my_kinopoisk.wrapper.MovieSearchWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    private GenreRepository genreRepository;

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private PersonService personService;
    @Autowired
    private MovieService movieService;

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

    public Genre getGenre() {
        var genre = new Genre();
        genre.setTitle("action");
        return genre;
    }

    public Genre saveGenre() {
        return genreRepository.save(getGenre());
    }

    public Movie getOneMovieWithSomeData() {
        var movieId = 1L;
        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));
        return movie;
    }

    public Movie saveOneMovieWithSomeData() {
        return movieService.saveMovie(getOneMovieWithSomeData());
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void searchMovieByTitle() throws Exception {
        var movie = saveOneMovieWithSomeData();

        var wrapper = new MovieSearchWrapper();
        wrapper.setTitle("e");

        wrapper.setValidatedList(null);

        var requestContent = objectMapper.writeValueAsString(wrapper);

        var expectedResponse = objectMapper.writeValueAsString(
            new PageImpl<>(List.of(movieMapper.toInListDto(movie))));


        mockMvc.perform(post("/search/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void searchMovieByGenre() throws Exception {
        saveOneMovieWithSomeData();

        var wrapper = new MovieSearchWrapper();
        wrapper.setTitle("e");

        var genre = saveGenre();
        genre.setTitle(null);
        wrapper.setValidatedList(
            new ArrayList<>(List.of(genreMapper.toDto(genre))));

        var requestContent = objectMapper.writeValueAsString(wrapper);

        var expectedResponse = objectMapper.writeValueAsString(
            new PageImpl<>(new ArrayList<>()));


        mockMvc.perform(post("/search/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().is(200))
            .andExpect(content().string(expectedResponse));
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void searchPerson() throws Exception {
        var person = savePersonWithSomeData();

        var expectedResponse = objectMapper.writeValueAsString(
            new PageImpl<>(
                new ArrayList<>(
                    List.of(personMapper.toPersonInListDto(person)))
            )
        );

        String title = "a";
        mockMvc.perform(get("/search/person/" + title))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

}
