package com.example.my_kinopoisk.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class MovieNotFoundProblem extends AbstractThrowableProblem {


    public static String currentURI = "https://localhost:8080/";


    private static final URI TYPE
        = URI.create(currentURI);

    public MovieNotFoundProblem(Long movieId) {
        super(
            TYPE,
            "Not found",
            Status.NOT_FOUND,
            String.format("Movie '%s' not found", movieId));
    }

}
