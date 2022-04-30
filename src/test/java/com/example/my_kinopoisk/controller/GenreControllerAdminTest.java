package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {"read", "write"})
public class GenreControllerAdminTest extends MyKinopoiskApplicationTests {

    String genreNotFound = "Genre was not found";


    ErrorResponse genreNotFoundError;

    String genreNotFoundMessage;


    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreMapper genreMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    public void clean() {
        cleanTables();
    }

    @PostConstruct
    void initMessages() throws JsonProcessingException {
        genreNotFoundError = new ErrorResponse(genreNotFound);
        genreNotFoundMessage = objectMapper.writeValueAsString(genreNotFoundError);
    }

    @Test
    public void saveGenreSuccess() throws Exception {
        var genre = new Genre();
        genre.setTitle("hdsfgihd");
        var requestContent = objectMapper.writeValueAsString(genre);

        genre.setId(1000L);
        var expectedResponse = objectMapper.writeValueAsString(genre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @Test
    public void saveGenreUnSuccess() throws Exception {
        var genre = new Genre();
        genre.setTitle("hdsfgihd");
        genre.setId(1000L);
        var requestContent = objectMapper.writeValueAsString(genre);

        var expectedResponse = objectMapper.writeValueAsString(genre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().is(400));
    }

    @Test
    @Sql(statements = "insert into genre(id, title) values(1,'title')")
    public void getGenres() throws Exception {
        var genre = new Genre();
        genre.setId(1L);
        genre.setTitle("title");

        var genreList = List.of(genreMapper.toDto(genre));

        var genrePage = new PageImpl<>(genreList);

        var expectedResponse = objectMapper.writeValueAsString(genrePage);

        mockMvc.perform(get("/genre"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @Test
    @Sql(statements = "insert into genre(id, title) values(1,'title')")
    public void deleteGenreSuccess() throws Exception {
        var answer = jdbcTemplate.queryForObject("select EXISTS(select 1 from genre where id = 1)", Boolean.class);
        Assertions.assertEquals(Boolean.TRUE, answer);

        mockMvc.perform(delete("/genre/1"))
            .andExpect(status().isNoContent());
        // to repository

        answer = jdbcTemplate.queryForObject("select EXISTS(select 1 from genre where id = 1)", Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, answer);

    }

    @Test
    public void deleteGenreNotSuccess() throws Exception {

        mockMvc.perform(delete("/genre/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(genreNotFoundMessage));

        var answer = jdbcTemplate.queryForObject("select EXISTS(select 1 from genre where id = 1)", Boolean.class);
        Assertions.assertEquals(Boolean.FALSE, answer);

    }
}
