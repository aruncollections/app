package com.app.service.security;

import static com.app.domain.user.Role.ROLE_NAME.USER;

import com.app.api.dto.UserInfo;
import com.app.domain.user.Role;
import com.app.domain.user.RoleRepository;
import com.app.domain.user.User;
import com.app.domain.user.UserRepository;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  private Role roleEntity;

  @PostConstruct
  void init() {
    if (roleEntity == null) {
      roleEntity = roleRepository.findByName(USER.name());
    }
  }

  public Optional<User> getCurrentUser() {
    val authentication = SecurityContextHolder.getContext().getAuthentication();
    val username = authentication.getName();
    return userRepository.findByEmailId(username);
  }

  @Transactional(readOnly = true)
  public Optional<User> getCurrentUser(final Principal principal) {
    return userRepository
        .findByEmailId(principal.getName())
        .map(
            user -> {
              user.setPassword(null);
              return user;
            });
  }

  @Transactional
  public User createNewUser(UserInfo userInfo) {
    log.trace("User credential {}, {}", userInfo.getEmailId(), userInfo.getPassword());

    val userOptional = userRepository.findByEmailId(userInfo.getEmailId().trim());

    if (userOptional.isPresent()) {
      val user = userOptional.get();
      user.setPassword(passwordEncoder.encode(userInfo.getPassword().trim()));
      user.setFirstName(userInfo.getFirstName().trim());
      user.setLastName(userInfo.getLastName().trim());
      user.setActive(false);
      log.info(
          "Updating user {}, {} {}",
          user.getEmailId(),
          userInfo.getFirstName(),
          userInfo.getLastName());
      return user;
    }
    ;

    // TODO
    val roleEntity = roleRepository.findByName(USER.name());

    val user =
        User.builder()
            .emailId(userInfo.getEmailId().trim())
            .password(passwordEncoder.encode(userInfo.getPassword().trim()))
            .firstName(userInfo.getFirstName().trim())
            .lastName(userInfo.getLastName().trim())
            .roles(Set.of(roleEntity))
            .active(false)
            .build();
    log.info(
        "Creating user {}, {} {}",
        user.getEmailId(),
        userInfo.getFirstName(),
        userInfo.getLastName());
    return userRepository.save(user);
  }

  @Transactional
  public void setActiveStatus(String emailId, boolean flag) {
    userRepository
        .findByEmailId(emailId)
        .ifPresent(
            user -> {
              user.setActive(flag);
              log.info("User {}'s active status set to {}", emailId, flag);
            });
  }

  @Transactional(readOnly = true)
  public List<User> getAllUsers(Boolean isActive) {
    if (isActive == null) {
      return userRepository.findAll();
    } else {
      return userRepository.findAllByActive(isActive);
    }
  }
}
