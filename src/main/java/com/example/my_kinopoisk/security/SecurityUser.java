package com.example.my_kinopoisk.security;


import com.example.my_kinopoisk.domain.entity.security.Status;
import com.example.my_kinopoisk.domain.entity.security.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class SecurityUser implements UserDetails {

    private final String username;
    private final String password;
    private final String login;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public static UserDetails fromUser(User user) {
        return new org.springframework.security.core.userdetails.User(
            user.getUserName(), user.getPassword(),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getStatus().equals(Status.ACTIVE),
            user.getRole().getAuthorities()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public String getLogin(){ return login;}

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
