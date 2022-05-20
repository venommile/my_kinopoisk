package com.example.my_kinopoisk.repository;

import com.example.my_kinopoisk.domain.entity.security.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByLogin(String login);

    Optional<User> findByUserName(String userName);

    boolean existsUserByLoginOrUserName(String login, String userName);

}
