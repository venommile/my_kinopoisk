package com.example.my_kinopoisk.controller;

import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.domain.entity.Review;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.repository.ReviewRepository;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest extends MyKinopoiskApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private ReviewRepository reviewRepository;


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;


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
        return movieRepository.save(getOneMovieWithSomeData());
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void addReviewTest() throws Exception {
        var movieId = saveOneMovieWithSomeData().getId();

        var movie = new Movie();
        movie.setId(movieId);
        movie.setGenres(null);
        movie.setFilmCrews(null);
        movie.setActors(null);
        movie.setReleaseDate(null);
        movie.setAgeLimit(null);
        movie.setReviews(null);


        var review = new Review();
        review.setTitle("title");
        review.setMovie(movie);

        var requestContent = objectMapper.writeValueAsString(review);


        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userName").value("admin"))
            .andExpect(jsonPath("$.title").value("title"));
    }


    @WithMockUser(username = "admin", authorities = {"read", "delete_comments", "write"})
    @Test
    @Transactional
    public void deleteReview() throws Exception {

        var movieId = saveOneMovieWithSomeData().getId();

        var movie = new Movie();
        movie.setId(movieId);
        movie.setGenres(null);
        movie.setFilmCrews(null);
        movie.setActors(null);
        movie.setReleaseDate(null);
        movie.setAgeLimit(null);
        movie.setReviews(null);


        var review = new Review();
        review.setTitle("title");
        review.setMovie(movie);

        reviewRepository.save(review);

        Assertions.assertEquals(1L, reviewRepository.count());

        mockMvc.perform(delete("/review/" + review.getId())
            )
            .andExpect(status().is(204));


    }

    @WithMockUser(username = "user", authorities = {"read"})
    @Test
    @Transactional
    public void addReviewUserTest() throws Exception {
        var movieId = saveOneMovieWithSomeData().getId();

        var movie = new Movie();
        movie.setId(movieId);
        movie.setGenres(null);
        movie.setFilmCrews(null);
        movie.setActors(null);
        movie.setReleaseDate(null);
        movie.setAgeLimit(null);
        movie.setReviews(null);


        var review = new Review();
        review.setTitle("title");
        review.setMovie(movie);

        var requestContent = objectMapper.writeValueAsString(review);


        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk());
    }

    public void addReviewUser() throws Exception {
        var movieId = saveOneMovieWithSomeData().getId();

        var movie = new Movie();
        movie.setId(movieId);
        movie.setGenres(null);
        movie.setFilmCrews(null);
        movie.setActors(null);
        movie.setReleaseDate(null);
        movie.setAgeLimit(null);
        movie.setReviews(null);


        var review = new Review();
        review.setTitle("title");
        review.setMovie(movie);

        var requestContent = objectMapper.writeValueAsString(review);


        mockMvc.perform(post("/review")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", authorities = {"read"})
    @Test
    @Transactional
    public void deleteOwnReview() throws Exception {
        addReviewUser();
        var review = reviewRepository.findAll().get(0);

        var requestContent = objectMapper.writeValueAsString(review);


        mockMvc.perform(delete("/review/delete-own/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent)
            )
            .andExpect(status().is(204));


    }


}
