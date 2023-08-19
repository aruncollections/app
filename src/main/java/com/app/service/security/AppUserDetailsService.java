package com.app.service.security;

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
public class AppUserDetailsService implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
    User existingUser =
        userRepository
            .findByEmailId(emailId)
             .filter(user -> user.isActive())
            .orElseThrow(() -> new UsernameNotFoundException("User " + emailId + " is not found!"));

    return new AppUserDetails(existingUser);
  }
}
