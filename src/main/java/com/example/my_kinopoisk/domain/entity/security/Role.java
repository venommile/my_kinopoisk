package com.example.my_kinopoisk.domain.entity.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum Role {
//    USER(Set.of(Permission.READ)),
//    ADMIN(Set.of(Permission.READ, Permission.WRITE));
//
//    private final Set<Permission> permissions;
//
//    public Set<SimpleGrantedAuthority> getAuthorities() {
//        return getPermissions().stream()
//            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
//            .collect(Collectors.toSet());
//    }
}
