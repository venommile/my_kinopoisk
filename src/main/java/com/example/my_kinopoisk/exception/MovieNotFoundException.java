package com.example.my_kinopoisk.exception;

public class MovieNotFoundException extends RuntimeException{
    public MovieNotFoundException(){
        super("Movie was not found");
    }
}
