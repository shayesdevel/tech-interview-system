package com.interviewsystem.entity;

import jakarta.persistence.*;
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
 * Submission entity representing a candidate's code submission for a problem.
 *
 * <p>Tracks the submitted code, programming language, compilation status, and submission
 * timestamp. Each submission is evaluated separately.
 */
@Entity
@Table(name = "submission", indexes = {
    @Index(name = "idx_candidate_id", columnList = "candidate_id"),
    @Index(name = "idx_problem_id", columnList = "problem_id"),
    @Index(name = "idx_candidate_problem", columnList = "candidate_id, problem_id"),
    @Index(name = "idx_submitted_at", columnList = "submitted_at"),
    @Index(name = "idx_compilation_status", columnList = "compilation_status")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"candidate", "problem", "evaluation"})
@ToString(exclude = {"candidate", "problem", "evaluation"})
public class Submission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "candidate_id", nullable = false)
  @NotNull(message = "Candidate is required")
  private Candidate candidate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "problem_id", nullable = false)
  @NotNull(message = "Problem is required")
  private Problem problem;

  @Column(name = "code_content", nullable = false, columnDefinition = "TEXT")
  @NotBlank(message = "Code content is required")
  private String codeContent;

  @Column(nullable = false, length = 20)
  @NotBlank(message = "Language is required")
  private String language;

  @Column(name = "submitted_at", nullable = false)
  @NotNull(message = "Submitted at timestamp is required")
  private LocalDateTime submittedAt;

  @Column(name = "compilation_status", nullable = false, length = 20)
  @Builder.Default
  private String compilationStatus = "PENDING";

  @Column(name = "compilation_error", columnDefinition = "TEXT")
  private String compilationError;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

  @OneToOne(mappedBy = "submission", cascade = CascadeType.ALL, orphanRemoval = true)
  private Evaluation evaluation;

  /**
   * Pre-persist hook to set creation and submission timestamps.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
    if (submittedAt == null) {
      submittedAt = LocalDateTime.now();
    }
  }

  /**
   * Pre-update hook to update modification timestamp.
   */
  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}
