package com.app.domain.investment;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvestmentDataRepository extends JpaRepository<InvestmentData, Long> {

  Optional<InvestmentData> findByUserEmailId(String emailId);

  List<InvestmentData> findAllByUserEmailId(String emailId);

  List<InvestmentData> findByUserEmailIdIn(List<String> emailIds);
}
