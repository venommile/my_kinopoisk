package com.example.my_kinopoisk.problem;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class PersonNotFoundProblem extends AbstractThrowableProblem {


    private static String currentURI = "https://localhost:8080/"; //get from .yml file


    private static final URI TYPE
        = URI.create(currentURI);

    public PersonNotFoundProblem(Long personId) {
        super(
            TYPE,
            "Not found",
            Status.NOT_FOUND,
            String.format("Person '%s' not found", personId));
    }

}
