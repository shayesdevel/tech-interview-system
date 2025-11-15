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
 * Interviewer entity representing an interviewer in the system.
 *
 * <p>Stores interviewer profile information including role, expertise areas, and active status.
 * Interviewers can perform manual code quality reviews and provide feedback on submissions.
 */
@Entity
@Table(name = "interviewer", indexes = {
    @Index(name = "idx_email", columnList = "email"),
    @Index(name = "idx_active", columnList = "active"),
    @Index(name = "idx_role", columnList = "role")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "evaluations")
@ToString(exclude = "evaluations")
public class Interviewer {

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

  @Column(nullable = false, length = 30)
  @NotBlank(message = "Role is required")
  @Builder.Default
  private String role = "INTERVIEWER";

  @Column(name = "expertise_areas", length = 500)
  private String expertiseAreas;

  @Column(nullable = false)
  @Builder.Default
  private Boolean active = true;

  @Column(name = "created_at", nullable = false, updatable = false)
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column(name = "updated_at", nullable = false)
  @Builder.Default
  private LocalDateTime updatedAt = LocalDateTime.now();

  @OneToMany(mappedBy = "interviewer", cascade = CascadeType.REFRESH)
  private List<Evaluation> evaluations = new ArrayList<>();

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
