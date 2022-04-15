package com.example.my_kinopoisk.service.mapper;

import com.example.my_kinopoisk.domain.dto.UserDto;
import com.example.my_kinopoisk.domain.entity.security.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}
