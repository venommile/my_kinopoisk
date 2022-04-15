package com.example.my_kinopoisk.exception;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException() {
        super("Person was not found");
    }
}
