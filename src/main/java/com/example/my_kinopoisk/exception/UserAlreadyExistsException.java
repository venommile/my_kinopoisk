package com.example.my_kinopoisk.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("Sorry, user with this login or username is already exists");
    }
}
