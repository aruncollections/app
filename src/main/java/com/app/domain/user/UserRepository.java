package com.app.domain.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByEmailId(String emailId);

  Optional<User> findByEmailId(String emailId);

  List<User> findAllByActive(boolean isActive);
}
