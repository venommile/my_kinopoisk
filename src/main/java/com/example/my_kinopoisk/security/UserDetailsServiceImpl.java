package com.example.my_kinopoisk.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl {

//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return SecurityUser.fromUser(userRepository.findByLogin(username)
//            .orElseThrow(() -> new UsernameNotFoundException("Could not find user")));
//    }
}
