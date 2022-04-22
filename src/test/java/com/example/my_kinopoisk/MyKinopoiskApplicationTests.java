package com.example.my_kinopoisk;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


@DirtiesContext
@SpringBootTest
@Testcontainers
public class MyKinopoiskApplicationTests {


    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:14.1").withReuse(true);
    @Autowired
    SpringLiquibase liquibase;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }


    @BeforeAll
    public static void beforeAll() {
        postgresContainer.start();

    }

    public void cleanTables() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "movie_genres", "actor", "genre", "film_crew", "movie", "genre_persons", "person",
            "rating", "review");
        jdbcTemplate.execute("SELECT setval('hibernate_sequence', 1000, false )");
    }

}
