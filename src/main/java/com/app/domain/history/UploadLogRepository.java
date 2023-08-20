package com.app.domain.history;

import com.app.domain.investment.InvestmentData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UploadLogRepository extends JpaRepository<UploadLog, Long> {

  List<UploadLog> findByEmailId(String emailId);
}
