package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WithMockUser(username = "user", authorities = {"read"})
public class GenreControllerUserTest extends MyKinopoiskApplicationTests {

    String genreNotFound = "Genre was not found";


    ErrorResponse genreNotFoundError;

    String genreNotFoundMessage;


    @Autowired
    private ObjectMapper objectMapper;


    @PostConstruct
    void initMessages() throws JsonProcessingException {
        genreNotFoundError = new ErrorResponse(genreNotFound);
        genreNotFoundMessage = objectMapper.writeValueAsString(genreNotFoundError);
    }

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void saveGenreSuccess() throws Exception {
        var genre = new Genre();
        genre.setTitle("hdsfgihd");
        var requestContent = objectMapper.writeValueAsString(genre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isForbidden());

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
    public void deleteGenreSuccess() throws Exception {
        mockMvc.perform(delete("/genre/1"))
            .andExpect(status().isForbidden());
    }


}
