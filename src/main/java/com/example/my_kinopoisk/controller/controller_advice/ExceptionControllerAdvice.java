package com.example.my_kinopoisk.controller.controller_advice;

import com.example.my_kinopoisk.exception.GenreNotFoundException;
import com.example.my_kinopoisk.exception.MovieNotFoundException;
import com.example.my_kinopoisk.exception.PersonNotFoundException;
import com.example.my_kinopoisk.message.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler({GenreNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(GenreNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({MovieNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(MovieNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({PersonNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(PersonNotFoundException exception) {
        return ResponseEntity.status(NOT_FOUND).body(new ErrorResponse(exception.getMessage()));
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> onMethodArgumentNotValidException(ConstraintViolationException exception) {
        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResponse(exception.getMessage()));
    }
}
