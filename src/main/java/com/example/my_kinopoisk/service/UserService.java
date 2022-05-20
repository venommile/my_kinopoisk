package com.example.my_kinopoisk.service;


import com.example.my_kinopoisk.domain.dto.UserDto;
import com.example.my_kinopoisk.domain.entity.security.Role;
import com.example.my_kinopoisk.domain.entity.security.Status;
import com.example.my_kinopoisk.domain.entity.security.User;
import com.example.my_kinopoisk.exception.UserAlreadyExistsException;
import com.example.my_kinopoisk.repository.UserRepository;
import com.example.my_kinopoisk.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserDto getUser(String userName) {
        return userRepository.findByUserName(userName)
            .map(userMapper::toDto)
            .orElseThrow();
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public UserDto saveView(User user){
        if(userRepository.existsUserByLoginOrUserName(user.getLogin(), user.getUserName())) throw new UserAlreadyExistsException();
        user.setRole(Role.USER);

        user.setStatus(Status.ACTIVE);

        user.setPassword(encoder.encode(user.getPassword()));

        return userMapper.toDto(userRepository.save(user));
    }


}
