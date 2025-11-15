package com.interviewsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Evaluation entity representing the evaluation results for a code submission.
 *
 * <p>Captures automated test results (execution score), code quality assessments, and overall
 * scores. Can include manual reviewer feedback from an interviewer.
 */
@Entity
@Table(name = "evaluation", indexes = {
    @Index(name = "idx_submission_id", columnList = "submission_id"),
    @Index(name = "idx_interviewer_id", columnList = "interviewer_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_evaluated_at", columnList = "evaluated_at")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"submission", "interviewer"})
@ToString(exclude = {"submission", "interviewer"})
public class Evaluation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "submission_id", nullable = false, unique = true)
  @NotNull(message = "Submission is required")
  private Submission submission;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "interviewer_id")
  private Interviewer interviewer;

  @Column(name = "test_pass_count", nullable = false)
  @Builder.Default
  private Integer testPassCount = 0;

  @Column(name = "total_test_cases", nullable = false)
  @NotNull(message = "Total test cases is required")
  private Integer totalTestCases;

  @Column(name = "execution_score", nullable = false)
  @Builder.Default
  @Min(value = 0, message = "Execution score must be at least 0")
  private Integer executionScore = 0;

  @Column(name = "code_quality_score", nullable = false)
  @Builder.Default
  @Min(value = 0, message = "Code quality score must be at least 0")
  private Integer codeQualityScore = 0;

  @Column(name = "overall_score", nullable = false)
  @Builder.Default
  @Min(value = 0, message = "Overall score must be at least 0")
  private Integer overallScore = 0;

  @Column(name = "feedback", columnDefinition = "TEXT")
  private String feedback;

  @Column(nullable = false, length = 20)
  @Builder.Default
  private String status = "PENDING";

  @Column(name = "evaluated_at")
  private LocalDateTime evaluatedAt;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

  /**
   * Pre-persist hook to set creation timestamp.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
  }

  /**
   * Pre-update hook to update modification timestamp.
   */
  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
