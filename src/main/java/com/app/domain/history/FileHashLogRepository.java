package com.app.domain.history;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileHashLogRepository extends JpaRepository<FileHashLog, Long> {

  boolean existsByHash(String hash);
}
