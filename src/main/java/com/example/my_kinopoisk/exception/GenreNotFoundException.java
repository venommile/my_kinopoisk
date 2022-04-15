package com.example.my_kinopoisk.exception;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException() {
        super("Genre was not found");
    }
}
