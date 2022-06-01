package com.example.my_kinopoisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
//@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class MyKinopoiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyKinopoiskApplication.class, args);
    }

}
