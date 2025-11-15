package com.interviewsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * TestCase entity representing a test case for a problem.
 *
 * <p>Defines input/output test cases used to evaluate candidate submissions. Test cases have a
 * weight and can be marked as hidden (not visible to candidates).
 */
@Entity
@Table(name = "test_case", indexes = {@Index(name = "idx_problem_id", columnList = "problem_id")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "problem")
@ToString(exclude = "problem")
public class TestCase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id", nullable = false)
  @NotNull(message = "Problem is required")
  private Problem problem;

  @Column(name = "input_description", nullable = false, length = 1000)
  @NotBlank(message = "Input description is required")
  private String inputDescription;

  @Column(name = "expected_output", nullable = false, length = 1000)
  @NotBlank(message = "Expected output is required")
  private String expectedOutput;

  @Column(name = "weight_percentage", nullable = false)
  @NotNull(message = "Weight percentage is required")
  @Min(value = 1, message = "Weight percentage must be at least 1")
  @Builder.Default
  private Integer weightPercentage = 10;

  @Column(name = "is_hidden", nullable = false)
  @Builder.Default
  private Boolean isHidden = false;

  @Column(name = "timeout_seconds", nullable = false)
  @NotNull(message = "Timeout seconds is required")
  @Min(value = 1, message = "Timeout must be at least 1 second")
  @Builder.Default
  private Integer timeoutSeconds = 5;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  /**
   * Pre-persist hook to set creation timestamp.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }
}
