package com.interviewsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Problem entity representing an interview problem in the system.
 *
 * <p>Defines interview problems with difficulty, category, technology stack, and test cases. Each
 * problem can have multiple submissions from different candidates.
 */
@Entity
@Table(name = "problem", indexes = {
    @Index(name = "idx_difficulty", columnList = "difficulty"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_primary_tech_stack", columnList = "primary_tech_stack"),
    @Index(name = "idx_title", columnList = "title")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"testCases", "submissions"})
@ToString(exclude = {"testCases", "submissions"})
public class Problem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 255)
  @NotBlank(message = "Title is required")
  @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  @NotBlank(message = "Description is required")
  private String description;

  @Column(nullable = false, length = 20)
  @NotBlank(message = "Difficulty is required")
  @Builder.Default
  private String difficulty = "MEDIUM";

  @Column(nullable = false, length = 50)
  @NotBlank(message = "Category is required")
  private String category;

  @Column(name = "primary_tech_stack", nullable = false, length = 20)
  @NotBlank(message = "Primary tech stack is required")
  private String primaryTechStack;

  @Column(name = "solution_approach", columnDefinition = "TEXT")
  private String solutionApproach;

  @Column(name = "estimated_minutes", nullable = false)
  @NotNull(message = "Estimated minutes is required")
  @Min(value = 1, message = "Estimated minutes must be at least 1")
  @Builder.Default
  private Integer estimatedMinutes = 60;

  @Column(name = "max_score", nullable = false)
  @NotNull(message = "Max score is required")
  @Min(value = 1, message = "Max score must be at least 1")
  @Builder.Default
  private Integer maxScore = 100;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TestCase> testCases = new ArrayList<>();

  @OneToMany(mappedBy = "problem", cascade = CascadeType.REFRESH)
  private List<Submission> submissions = new ArrayList<>();

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
