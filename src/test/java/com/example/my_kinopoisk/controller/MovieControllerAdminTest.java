package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.entity.Actor;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
//@Transactional
//Если мы поставим Transactional, то JDBC template не будет видеть что реально происходит в базе
@WithMockUser(username = "admin", authorities = {"read", "write"})
public class MovieControllerAdminTest extends MyKinopoiskApplicationTests {

    String movieNotFound = "Movie was not found";
    String genreNotFound = "Genre was not found";

    ErrorResponse movieNotFoundError;
    ErrorResponse genreNotFoundError;

    String movieNotFoundMessage;
    String genreNotFoundMessage;

    @PostConstruct
    void initMessages() throws JsonProcessingException {
        movieNotFoundError = new ErrorResponse(movieNotFound);
        genreNotFoundError = new ErrorResponse(genreNotFound);

        movieNotFoundMessage = objectMapper.writeValueAsString(movieNotFoundError);
        genreNotFoundMessage = objectMapper.writeValueAsString(genreNotFoundError);
    }


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Test
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void getMovieFound() throws Exception {
        var movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var expectedResponse = objectMapper.writeValueAsString(movie);

        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @Test
    public void getMovieNotFound() throws Exception {
        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }


    @Test
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void getMoviesFound() throws Exception {
        var movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var movieList = new ArrayList<MovieInListDto>();

        movieList.add(movieMapper.toInListDto(movie));
        var movieAns = new PageImpl<>(movieList);

        var expectedResponse = objectMapper.writeValueAsString(movieAns);

        mockMvc.perform(get("/movies/"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }

    @Test
    public void saveMovieSuccessWithoutInnerEntities() throws Exception {
        var movie = new Movie();
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var answer = jdbcTemplate.queryForObject("select EXISTS(select 1 from movie where id = 1000)", Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, answer);

        var requestContent = objectMapper.writeValueAsString(movie);
        movie.setId(1000L);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

        var answer2 = jdbcTemplate.queryForObject("select Exists( select 1 from movie where id = 1000)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer2);

    }

    @Test
    public void saveMovieWithInnerEntities() throws Exception {
        var movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var genre = new Genre();
        genre.setTitle("drama");

        var actor = new Actor();
        actor.setSurname("actor surname");
        actor.setName("actor name");
        actor.setRole("role");

        var actorSet = new HashSet<Actor>();
        actorSet.add(actor);
        movie.setActors(actorSet);

        var genreSet = new HashSet<Genre>();
        genreSet.add(genre);
        movie.setGenres(genreSet);

        var requestContent = objectMapper.writeValueAsString(movie);

        genre.setId(1002L);
        movie.setId(1003L);
        actor.setId(1001L);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

        var answer1 = jdbcTemplate.queryForObject("select EXISTS(select 1 from movie where id = 1003)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer1);

        var answer2 = jdbcTemplate.queryForObject("select EXISTS(select 1 from genre where id = 1002)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer2);

        var answer3 = jdbcTemplate.queryForObject("select EXISTS( select 1 from actor where id = 1001)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer3);
    }


    @Test
    public void deleteMoviesNotSuccess() throws Exception {
        mockMvc.perform(delete("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }

    @Test
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void deleteMoviesSuccess() throws Exception {
        var answer1 = jdbcTemplate.queryForObject("select EXISTS(select 1 from movie where id = 1)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer1);

        mockMvc.perform(delete("/movies/" + 1))
            .andExpect(status().isNoContent());

        answer1 = jdbcTemplate.queryForObject("select EXISTS(select 1 from movie where id = 1)", Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, answer1);
    }

    @Test
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    @Sql(statements = "insert into genre (id, title) values (1, 'action')")
    public void bindGenreToMovieSuccess() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;
        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var genre = new Genre();
        genre.setId(1L);
        genre.setTitle("action");

        movie.setGenres(Set.of(genre));

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));
        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @Test
    @Sql(statements = "insert into genre (id, title) values (1, 'action')")
    public void bindGenreToMovieNotSuccessMovie() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }

    @Test
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void bindGenreToMovieNotSuccessGenre() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isNotFound())
            .andExpect(content().string(genreNotFoundMessage));
    }


}