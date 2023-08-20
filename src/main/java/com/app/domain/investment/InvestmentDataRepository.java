package com.app.domain.investment;

import com.app.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvestmentDataRepository extends JpaRepository<InvestmentData, Long> {

  Optional<InvestmentData> findByUserEmailId(String emailId);

  List<InvestmentData> findAllByUserEmailId(String emailId);

  List<InvestmentData> findByUserEmailIdIn(List<String> emailIds);
}
