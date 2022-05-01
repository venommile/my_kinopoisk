package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.UserDto;
import com.example.my_kinopoisk.repository.UserRepository;
import com.example.my_kinopoisk.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUser(String userName) {
        return userRepository.findByUserName(userName)
            .map(userMapper::toDto)
            .orElseThrow();
    }
}
