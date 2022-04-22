package com.example.my_kinopoisk.validation;

import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


public class ValidatedList<T> implements List<T>{
    @Valid
    @Delegate
    private final List<T> list = new ArrayList<>();

}
