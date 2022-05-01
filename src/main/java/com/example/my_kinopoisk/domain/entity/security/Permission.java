package com.example.my_kinopoisk.domain.entity.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    READ("read"),
    DELETE_COMMENTS("delete_comments"),
    WRITE("write");

    private final String permission;

}
