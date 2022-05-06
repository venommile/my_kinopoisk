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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
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
@Transactional
@Rollback
public class MovieControllerTest extends MyKinopoiskApplicationTests {

    String movieNotFound = "Movie was not found";
    String genreNotFound = "Genre was not found";

    ErrorResponse movieNotFoundError;
    ErrorResponse genreNotFoundError;

    String movieNotFoundMessage;
    String genreNotFoundMessage;

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

    @PostConstruct
    void initMessages() throws JsonProcessingException {
        movieNotFoundError = new ErrorResponse(movieNotFound);
        genreNotFoundError = new ErrorResponse(genreNotFound);

        movieNotFoundMessage = objectMapper.writeValueAsString(movieNotFoundError);
        genreNotFoundMessage = objectMapper.writeValueAsString(genreNotFoundError);
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMovieFoundAdmin() throws Exception {
        var movieId = 1000L;
        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        movieRepository.save(movie);
        var expectedResponse = objectMapper.writeValueAsString(movie);

        mockMvc.perform(get("/movies/" + 1000))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMovieNotFoundAdmin() throws Exception {
        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void getMoviesFoundAdmin() throws Exception {
        var movieId = 1000L;
        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        movieRepository.save(movie);

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
        var movie = new Movie();
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        Assertions.assertEquals(0L, genreRepository.count());

        var requestContent = objectMapper.writeValueAsString(movie);
        movie.setId(1000L);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

        Assertions.assertEquals(movieRepository.getById(1000L).getTitle(), movie.getTitle());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void saveMovieWithInnerEntitiesAdmin() throws Exception {
        Long movieId = 1005L;
        Long genreId = 1004L;
        Long actorId = 1003L;
        Long filmCrewId = 1001L;
        var movie = new Movie();
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

        var filmCrew = new FilmCrew();
        filmCrew.setRole("some role");
        filmCrew.setName("some name");
        filmCrew.setSurname("some surname");
        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);


        genre.setId(genreId);
        movie.setId(movieId);
        actor.setId(actorId);

        filmCrew.setId(filmCrewId);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));


        Assertions.assertEquals(movieRepository.getById(1005L).getTitle(), movie.getTitle());

        Assertions.assertEquals(genreRepository.getById(1004L).getTitle(), genre.getTitle());

        Assertions.assertEquals(2L, personRepository.count());

        Assertions.assertEquals(filmCrewRepository.getById(1001L).getRole(), filmCrew.getRole());

        Assertions.assertEquals(actorRepository.getById(1003L).getRole(), actor.getRole());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void saveMovieWithInnerEntitiesWhenTwoRolesAndOnePersonAdmin() throws Exception {
        Long movieId = 1003L;

        Long actorId = 1002L;
        Long filmCrewId = 1001L;
        var movie = new Movie();
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


        var filmCrew = new FilmCrew();
        filmCrew.setRole("some role");
        filmCrew.setName("actor name");
        filmCrew.setSurname("actor surname");


        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);


        movie.setId(movieId);
        actor.setId(actorId);

        filmCrew.setId(filmCrewId);

        filmCrew.setId(filmCrewId);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

        mockMvc.perform(post("/movies/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestContent))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));

        Assertions.assertEquals(movieRepository.getById(1003L).getTitle(), movie.getTitle());


        Assertions.assertEquals(1L, personRepository.count());

        Assertions.assertEquals(filmCrewRepository.getById(1001L).getRole(), filmCrew.getRole());

        Assertions.assertEquals(actorRepository.getById(1002L).getRole(), actor.getRole());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void deleteMoviesNotSuccessAdmin() throws Exception {
        mockMvc.perform(delete("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void deleteMoviesSuccessAdmin() throws Exception {
        var movieId = 1000L;
        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        movieRepository.save(movie);


        Assertions.assertEquals(1L, movieRepository.count());


        mockMvc.perform(delete("/movies/" + 1000))
            .andExpect(status().isNoContent());

        Assertions.assertEquals(0L, movieRepository.count());

    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void bindGenreToMovieSuccessAdmin() throws Exception {

        var movieId = 1000L;
        var genreId = 1001L;

        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        var genre = new Genre();
        genre.setId(1001L);
        genre.setTitle("action");

        movieRepository.save(movie);
        movie.setGenres(Set.of(genre));
        genreRepository.save(genre);


        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));
        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }


    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional
    public void bindGenreToMovieNotSuccessMovieAdmin() throws Exception {
        var genre = new Genre();
        genre.setId(1001L);
        genre.setTitle("action");

        genreRepository.save(genre);
        Long movieId = 1000L;
        Long genreId = 1001L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }

    @WithMockUser(username = "admin", authorities = {"read", "write"})
    @Test
    @Transactional

    public void bindGenreToMovieNotSuccessGenreAdmin() throws Exception {
        var movieId = 1000L;


        var movie = new Movie();
        movie.setId(movieId);
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

        movieRepository.save(movie);

        Long genreId = 1001L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isNotFound())
            .andExpect(content().string(genreNotFoundMessage));
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void getMovieFoundUser() throws Exception {
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
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void getMovieNotFoundUser() throws Exception {
        mockMvc.perform(get("/movies/" + 1))
            .andExpect(status().isNotFound())
            .andExpect(content().string(movieNotFoundMessage));
    }


    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void getMoviesFoundUser() throws Exception {
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
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    public void saveMovieSuccessWithoutInnerEntitiesUser() throws Exception {
        var movie = new Movie();
        movie.setTitle("The Terminator");
        movie.setAgeLimit(16);
        movie.setCountryOfProduction("Country");
        movie.setDescription("desc");
        movie.setReleaseDate(LocalDate.parse("1984-10-26"));

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
        Long movieId = 1005L;
        Long genreId = 1004L;
        Long actorId = 1003L;
        Long filmCrewId = 1001L;
        var movie = new Movie();
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

        var filmCrew = new FilmCrew();
        filmCrew.setRole("some role");
        filmCrew.setName("some name");
        filmCrew.setSurname("some surname");
        var filmCrewSet = new HashSet<FilmCrew>();
        filmCrewSet.add(filmCrew);
        movie.setFilmCrews(filmCrewSet);


        var requestContent = objectMapper.writeValueAsString(movie);

        var expectedResponse = objectMapper.writeValueAsString(movieMapper.toViewDto(movie));

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
    @Sql(statements = "insert into genre (id, title) values (1, 'action')")
    public void bindGenreToMovieNotSuccessMovieUser() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isForbidden());
    }

    @Test
    @Transactional
    @WithMockUser(username = "user", authorities = {"read"})
    @Sql(statements = "insert into movie (id, title, age_limit, country_of_production, description, release_date) values(1,'The Terminator',16,'Country','desc','1984-10-26')")
    public void bindGenreToMovieNotSuccessGenreUser() throws Exception {
        Long movieId = 1L;
        Long genreId = 1L;

        mockMvc.perform(put("/movies/" + movieId + "/genre/" + genreId))
            .andExpect(status().isForbidden());
    }


}
