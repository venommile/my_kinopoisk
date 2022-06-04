package com.example.my_kinopoisk.problem;

import org.springframework.beans.factory.annotation.Value;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import javax.annotation.PostConstruct;
import java.net.URI;

public class GenreNotFoundProblem  extends AbstractThrowableProblem {


    private static String currentURI = "https://localhost:8080/"; //get from .yml file


    private static final URI TYPE
        = URI.create(currentURI);

    public GenreNotFoundProblem(Long genreId) {
        super(
            TYPE,
            "Not found",
            Status.NOT_FOUND,
            String.format("Genre '%s' not found", genreId));
    }

}
