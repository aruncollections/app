package com.app.domain.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findAll();

  Role findByName(String name);
}
