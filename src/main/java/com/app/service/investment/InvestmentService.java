package com.app.service.investment;

import static com.app.domain.user.Role.ROLE_NAME.ADMIN;
import static com.app.domain.user.Role.ROLE_NAME.USER;

import com.app.api.dto.InvestedInfo;
import com.app.domain.history.UploadLogRepository;
import com.app.domain.investment.InvestmentData;
import com.app.domain.investment.InvestmentDataRepository;
import com.app.domain.user.Role;
import com.app.domain.user.RoleRepository;
import com.app.domain.user.User;
import com.app.domain.user.UserRepository;
import com.app.service.security.UserService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvestmentService {

  private final EntityManager entityManager;
  private final InvestmentDataRepository investmentDataRepository;

  private final UploadLogRepository uploadLogRepository;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final UserService userService;

  private Role roleEntity;

  @PostConstruct
  void init() {
    if (roleEntity == null) {
      roleEntity = roleRepository.findByName(USER.name());
    }
  }

  @Transactional(readOnly = true)
  public List<InvestmentData> getInvestmentData() {
    val isAdmin =
        userService
            .getCurrentUser()
            .filter(
                u -> u.getRoles().stream().map(r -> r.getName()).toList().contains(ADMIN.name()))
            .isPresent();

    if (isAdmin) {
      log.trace("all data {}", investmentDataRepository.findAll().size());
      return investmentDataRepository.findAll();
    } else {
      return userService
          .getCurrentUser()
          .map(u -> investmentDataRepository.findAllByUserEmailId(u.getEmailId()))
          .get();
    }
  }

  @Transactional
  public void updateInvestmentData(List<InvestedInfo> investedInfos) {
    val emailIds =
        investedInfos.stream().map(infos -> infos.getEmailId().trim()).collect(Collectors.toList());
    val existingInvestmentData = investmentDataRepository.findByUserEmailIdIn(emailIds);
    val existingUserInvestmentDataMap =
        existingInvestmentData.stream()
            .collect(Collectors.toMap(i -> i.getUser().getEmailId(), Function.identity()));
    val usersMap =
        userRepository.findAll().stream()
            .collect(Collectors.toMap(i -> i.getEmailId(), Function.identity()));

    val investmentData =
        investedInfos.stream()
            .map(
                info -> {
                  if (existingUserInvestmentDataMap.get(info.getEmailId().trim()) != null) {
                    val invData = existingUserInvestmentDataMap.get(info.getEmailId().trim());
                    invData.setInvestedAmount(info.getInvestedAmount());
                    invData.setUpdatedAmount(info.getUpdatedAmount());
                    invData.setUpdatedTime(LocalDateTime.now());
                    invData.setLastEditedBy(
                        userService.getCurrentUser().map(u -> u.getEmailId()).orElse("UNKNOWN"));
                    return invData;
                  } else {
                    return InvestmentData.builder()
                        .user(createAndGetUser(info.getEmailId().trim(), usersMap))
                        .instrument("NA")
                        .updatedTime(LocalDateTime.now())
                        .investedAmount(info.getInvestedAmount())
                        .updatedAmount(info.getUpdatedAmount())
                        .lastEditedBy(
                            userService.getCurrentUser().map(u -> u.getEmailId()).orElse("UNKNOWN"))
                        .build();
                  }
                })
            .collect(Collectors.toList());

    investmentDataRepository.saveAll(investmentData);
  }

  private User createAndGetUser(String emailId, Map<String, User> userMap) {
    if (userMap.get(emailId) != null) {
      return userMap.get((emailId));
    } else {
      // TODO
      val roleEntity = roleRepository.findByName(USER.name());
      return userRepository.save(
          User.builder()
              .emailId(emailId)
              .isActive(false)
              .password("NONE")
              .roles(Set.of(roleEntity))
              .build());
    }
  }
}
