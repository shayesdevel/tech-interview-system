package com.interviewsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
 * Candidate entity representing a job candidate in the interview system.
 *
 * <p>Tracks candidate profile information including contact details and primary technology stack.
 */
@Entity
@Table(name = "candidate", indexes = {@Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_tech_stack", columnList = "tech_stack")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "submissions")
@ToString(exclude = "submissions")
public class Candidate {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 255)
  @Email(message = "Email should be valid")
  @NotBlank(message = "Email is required")
  private String email;

  @Column(name = "first_name", nullable = false, length = 100)
  @NotBlank(message = "First name is required")
  @Size(min = 1, max = 100, message = "First name must be between 1 and 100 characters")
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  @NotBlank(message = "Last name is required")
  @Size(min = 1, max = 100, message = "Last name must be between 1 and 100 characters")
  private String lastName;

  @Column(name = "phone_number", length = 20)
  private String phoneNumber;

  @Column(name = "tech_stack", nullable = false, length = 20)
  @Builder.Default
  private String techStack = "JAVA";

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, orphanRemoval = true)
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
