package com.app.service.security;

import java.util.ArrayList;

import com.app.domain.user.User;
import com.app.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User existingUser = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                existingUser.getEmail(), existingUser.getPassword(), new ArrayList<>());
    }

}