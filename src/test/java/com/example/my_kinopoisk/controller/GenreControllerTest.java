package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.repository.GenreRepository;
import com.example.my_kinopoisk.service.mapper.GenreMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.annotation.PostConstruct;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
public class GenreControllerTest extends MyKinopoiskApplicationTests {

    String genreNotFound = "Genre was not found";


    ErrorResponse genreNotFoundError;


    String genreNotFoundMessage;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GenreMapper genreMapper;

    public Genre getGenre() {
        var genre = new Genre();
        genre.setTitle("action");
        return genre;
    }

    public Genre saveGenre() {
        return genreRepository.save(getGenre());
    }


    @PostConstruct
    void initMessages() throws JsonProcessingException {
        genreNotFoundError = new ErrorResponse(genreNotFound);
        genreNotFoundMessage = objectMapper.writeValueAsString(genreNotFoundError);
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void saveGenreSuccessAdmin() throws Exception {
        var genre = getGenre();
        var requestContent = objectMapper.writeValueAsString(genre);


        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(genre.getTitle()));

    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void saveGenreUnSuccessAdmin() throws Exception {
        var genre = getGenre();
        var requestContent = objectMapper.writeValueAsString(genre);


        var expectedResponse = objectMapper.writeValueAsString(genre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().is(200))
            .andExpect(jsonPath("$.title").value(genre.getTitle()));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void getGenresAdmin() throws Exception {
        var genre = saveGenre();

        var genreList = List.of(genreMapper.toDto(genre));

        var genrePage = new PageImpl<>(genreList);

        var expectedResponse = objectMapper.writeValueAsString(genrePage);

        mockMvc.perform(get("/genre"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void deleteGenreSuccessAdmin() throws Exception {
        var genre = saveGenre();

        Assertions.assertEquals(genreRepository.count(), 1L);


        mockMvc.perform(delete("/genre/" + genre.getId()))
            .andExpect(status().isNoContent());

        Assertions.assertEquals(genreRepository.count(), 0L);


    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", authorities = {"read", "write"})
    public void deleteGenreNotSuccessAdmin() throws Exception {

        mockMvc.perform(delete("/genre/1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string(genreNotFoundMessage));

        Assertions.assertEquals(genreRepository.count(), 0L);

    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void saveGenreSuccessUser() throws Exception {
        var genre = getGenre();
        var requestContent = objectMapper.writeValueAsString(genre);

        mockMvc.perform(post("/genre")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isForbidden());

    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void getGenresUser() throws Exception {
        var genre = saveGenre();

        var genreList = List.of(genreMapper.toDto(genre));

        var genrePage = new PageImpl<>(genreList);

        var expectedResponse = objectMapper.writeValueAsString(genrePage);

        mockMvc.perform(get("/genre"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @Test
    @WithMockUser(username = "user", authorities = {"read"})
    @Transactional
    public void deleteGenreSuccessUser() throws Exception {
        mockMvc.perform(delete("/genre/1"))
            .andExpect(status().isForbidden());
    }
}
