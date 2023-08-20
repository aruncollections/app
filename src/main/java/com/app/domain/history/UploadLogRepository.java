package com.app.domain.history;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadLogRepository extends JpaRepository<UploadLog, Long> {

  List<UploadLog> findByEmailId(String emailId);
}
