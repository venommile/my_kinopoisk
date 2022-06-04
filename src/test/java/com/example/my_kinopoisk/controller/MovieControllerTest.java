package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.MyKinopoiskApplicationTests;
import com.example.my_kinopoisk.domain.dto.MovieInListDto;
import com.example.my_kinopoisk.domain.entity.Actor;
import com.example.my_kinopoisk.domain.entity.FilmCrew;
import com.example.my_kinopoisk.domain.entity.Genre;
import com.example.my_kinopoisk.domain.entity.Movie;
import com.example.my_kinopoisk.message.ErrorResponse;
import com.example.my_kinopoisk.repository.ActorRepository;
import com.example.my_kinopoisk.repository.FilmCrewRepository;
import com.example.my_kinopoisk.repository.GenreRepository;
import com.example.my_kinopoisk.repository.MovieRepository;
import com.example.my_kinopoisk.repository.PersonRepository;
import com.example.my_kinopoisk.service.mapper.MovieMapper;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@Transactional
public class MovieControllerTest extends MyKinopoiskApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MovieMapper movieMapper;


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FilmCrewRepository filmCrewRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    public Actor getActorWithSomeData() {
        var actor = new Actor();
        actor.setSurname("actor surname");
        actor.setName("actor name");
        actor.setRole("role");
        return actor;
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

    public FilmCrew getFilmCrewWithData() {
        var filmCrew = new FilmCrew();
        filmCrew.setRole("some role");
        filmCrew.setName("some name");
        filmCrew.setSurname("some surname");
        return filmCrew;
    }

    public Genre getGenre() {
        var genre = new Genre();
        genre.setTitle("action");
        return genre;
    }

    public Genre saveGenre() {
        return genreRepository.save(getGenre());
    }

    public Movie saveOneMovieWithSomeData() {
        return movieRepository.save(getOneMovieWithSomeData());
    }



    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMovieFoundAdmin() throws Exception {
        var movie = saveOneMovieWithSomeData();
        var expectedResponse = objectMapper.writeValueAsString(movie);

        mockMvc.perform(get("/movies/" + movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMovieNotFoundAdmin() throws Exception {
        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.detail").value(String.format("Movie '%s' not found", 1)));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMoviesFoundAdmin() throws Exception {
        var movie = saveOneMovieWithSomeData();

        var movieList = new ArrayList<MovieInListDto>();

        movieList.add(movieMapper.toInListDto(movie));
        var movieAns = new PageImpl<>(movieList);

        var expectedResponse = objectMapper.writeValueAsString(movieAns);

        mockMvc.perform(get("/movies/"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void saveMovieSuccessWithoutInnerEntitiesAdmin() throws Exception {
        var movie = getOneMovieWithSomeData();

        Assertions.assertEquals(0L, genreRepository.count());

        var requestContent = objectMapper.writeValueAsString(movie);
        movie.setId(3L);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void saveMovieWithInnerEntitiesAdmin() throws Exception {

        var movie = getOneMovieWithSomeData();

        var genre = getGenre();

        var actor = getActorWithSomeData();

        var actorSet = new HashSet<Actor>();
        actorSet.add(actor);
        movie.setActors(actorSet);

        var genreSet = new HashSet<Genre>();
        genreSet.add(genre);
        movie.setGenres(genreSet);

        var filmCrew = getFilmCrewWithData();
        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);


        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(movie.getTitle()))
            .andExpect(jsonPath("$.actors", hasSize(1)))
            .andExpect(jsonPath("$.filmCrews", hasSize(1)));


        Assertions.assertEquals(movieRepository.findAll().get(0).getTitle(), movie.getTitle());

        Assertions.assertEquals(genreRepository.findAll().get(0).getTitle(), genre.getTitle());

        Assertions.assertEquals(2L, personRepository.count());

        Assertions.assertEquals(filmCrewRepository.findAll().get(0).getRole(), filmCrew.getRole());

        Assertions.assertEquals(actorRepository.findAll().get(0).getRole(), actor.getRole());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void saveMovieWithInnerEntitiesWhenTwoRolesAndOnePersonAdmin() throws Exception {

        var movie = getOneMovieWithSomeData();

        var actor = getActorWithSomeData();

        var actorSet = new HashSet<Actor>();
        actorSet.add(actor);
        movie.setActors(actorSet);


        var filmCrew = new FilmCrew();
        filmCrew.setRole("some role");
        filmCrew.setName("actor name");
        filmCrew.setSurname("actor surname");


        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);


        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("The Terminator"))
            .andExpect(jsonPath("$.actors", hasSize(1)))
            .andExpect(jsonPath("$.filmCrews", hasSize(1)));

        var movieInDb = movieRepository.findAll().get(0);
        Assertions.assertEquals(movieInDb.getTitle(), movie.getTitle());


        Assertions.assertEquals(1L, personRepository.count());

        Assertions.assertEquals(filmCrewRepository.findAll().get(0).getRole(), filmCrew.getRole());

        Assertions.assertEquals(actorRepository.findAll().get(0).getRole(), actor.getRole());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void deleteMoviesNotSuccessAdmin() throws Exception {
        mockMvc.perform(delete("/movies/" + 1))
            .andExpect(status().is5xxServerError());
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void deleteMoviesSuccessAdmin() throws Exception {
        var movie = saveOneMovieWithSomeData();


        Assertions.assertEquals(1L, movieRepository.count());


        mockMvc.perform(delete("/movies/" + movie.getId()))
            .andExpect(status().isNoContent());

        Assertions.assertEquals(0L, movieRepository.count());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void bindGenreToMovieSuccessAdmin() throws Exception {


        var movie = getOneMovieWithSomeData();

        var genre = saveGenre();
        movie = movieRepository.save(movie);
        movie.setGenres(Set.of(genre));


        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));
        mockMvc.perform(put("/movies/" + movie.getId() + "/genre/" + genre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void bindGenreToMovieNotSuccessMovieAdmin() throws Exception {
        var genre = saveGenre();
        Long movieId = 1000L;


        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genre.getId()))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.detail").value(String.format("Movie '%s' not found", movieId)));
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void bindGenreToMovieNotSuccessGenreAdmin() throws Exception {
        var movieId = 1L;

        saveOneMovieWithSomeData();

        Long genreId = 1L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.detail").value(String.format("Genre '%s' not found", genreId)));
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void getMovieFoundUser() throws Exception {
        var movie = saveOneMovieWithSomeData();

        var expectedResponse = objectMapper.writeValueAsString(movie);

        mockMvc.perform(get("/movies/" + movie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void getMovieNotFoundUser() throws Exception {
        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.detail").value(String.format("Movie '%s' not found", 1)));
    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void getMoviesFoundUser() throws Exception {
        var movie = saveOneMovieWithSomeData();

        var movieList = new ArrayList<MovieInListDto>();

        movieList.add(movieMapper.toInListDto(movie));
        var movieAns = new PageImpl<>(movieList);

        var expectedResponse = objectMapper.writeValueAsString(movieAns);

        mockMvc.perform(get("/movies/"))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void saveMovieSuccessWithoutInnerEntitiesUser() throws Exception {
        var movie = getOneMovieWithSomeData();

        Assertions.assertEquals(0L, movieRepository.count());

        var requestContent = objectMapper.writeValueAsString(movie);

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isForbidden());

    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void saveMovieWithInnerEntitiesUser() throws Exception {
        var movie = saveOneMovieWithSomeData();

        var genre = getGenre();

        var actor = getActorWithSomeData();

        var actorSet = new HashSet<Actor>();
        actorSet.add(actor);
        movie.setActors(actorSet);

        var genreSet = new HashSet<Genre>();
        genreSet.add(genre);
        movie.setGenres(genreSet);

        var filmCrew = getFilmCrewWithData();
        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);


        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isForbidden());

    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void deleteMoviesNotSuccessUser() throws Exception {
        mockMvc.perform(delete("/movies/" + 1))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void deleteMoviesSuccessUser() throws Exception {
        mockMvc.perform(delete("/movies/" + 1)).andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void bindGenreToMovieSuccessUser() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;
        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isForbidden());
    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void bindGenreToMovieNotSuccessMovieUser() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;
        saveGenre();


        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void bindGenreToMovieNotSuccessGenreUser() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;
        saveOneMovieWithSomeData();

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isForbidden());
    }


}
