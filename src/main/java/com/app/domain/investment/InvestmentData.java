package com.app.domain.investment;

import com.app.domain.user.User;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table
public class InvestmentData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(
      optional = false,
      cascade = {CascadeType.ALL})
  private User user;

  @Column(nullable = false)
  private String instrument;

  @Column(nullable = false)
  private Double investedAmount;

  @Column(nullable = false)
  private Double updatedAmount;

  @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime updatedTime;

  private String lastEditedBy;
}
