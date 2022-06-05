package com.example.my_kinopoisk.controller;


import com.example.my_kinopoisk.domain.dto.UserDto;
import com.example.my_kinopoisk.domain.entity.security.User;
import com.example.my_kinopoisk.service.UserService;
import com.example.my_kinopoisk.validation.OnCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    @PostMapping
    @Validated(OnCreate.class)
    public ResponseEntity<UserDto> save(@Valid @RequestBody User user) {

        return ResponseEntity.ok(userService.save(user));

    }


}
