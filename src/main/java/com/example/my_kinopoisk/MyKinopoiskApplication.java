package com.example.my_kinopoisk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class MyKinopoiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyKinopoiskApplication.class, args);
    }

}
