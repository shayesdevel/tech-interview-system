package com.interviewsystem.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Entity mapping tests for Candidate entity.
 */
@DisplayName("Candidate Entity Tests")
class CandidateEntityTest {

  private Candidate candidate;

  @BeforeEach
  void setUp() {
    candidate = new Candidate();
  }

  @Test
  @DisplayName("Should create candidate with all fields")
  void testCreateCandidateWithAllFields() {
    // Arrange
    String email = "john.doe@example.com";
    String firstName = "John";
    String lastName = "Doe";
    String phoneNumber = "123-456-7890";
    String techStack = "JAVA";

    // Act
    candidate.setEmail(email);
    candidate.setFirstName(firstName);
    candidate.setLastName(lastName);
    candidate.setPhoneNumber(phoneNumber);
    candidate.setTechStack(techStack);

    // Assert
    assertEquals(email, candidate.getEmail());
    assertEquals(firstName, candidate.getFirstName());
    assertEquals(lastName, candidate.getLastName());
    assertEquals(phoneNumber, candidate.getPhoneNumber());
    assertEquals(techStack, candidate.getTechStack());
  }

  @Test
  @DisplayName("Should set timestamps on creation")
  void testTimestampsOnCreation() {
    // Act
    candidate.onCreate();

    // Assert
    assertNotNull(candidate.getCreatedAt());
    assertNotNull(candidate.getUpdatedAt());
    assertTrue(
        candidate.getCreatedAt().isBefore(LocalDateTime.now().plusSeconds(1)),
        "Created timestamp should be recent");
  }

  @Test
  @DisplayName("Should update timestamp on modification")
  void testTimestampOnUpdate() {
    // Arrange
    candidate.onCreate();
    LocalDateTime originalUpdatedAt = candidate.getUpdatedAt();

    // Act
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    candidate.onUpdate();

    // Assert
    assertNotEquals(
        originalUpdatedAt,
        candidate.getUpdatedAt(),
        "Updated timestamp should be different after update");
  }

  @Test
  @DisplayName("Should build candidate using builder")
  void testCandidateBuilder() {
    // Act
    Candidate builtCandidate = Candidate.builder()
        .email("jane.doe@example.com")
        .firstName("Jane")
        .lastName("Doe")
        .phoneNumber("987-654-3210")
        .techStack("TYPESCRIPT")
        .build();

    // Assert
    assertEquals("jane.doe@example.com", builtCandidate.getEmail());
    assertEquals("Jane", builtCandidate.getFirstName());
    assertEquals("Doe", builtCandidate.getLastName());
    assertEquals("987-654-3210", builtCandidate.getPhoneNumber());
    assertEquals("TYPESCRIPT", builtCandidate.getTechStack());
  }

  @Test
  @DisplayName("Should initialize submissions list")
  void testSubmissionsListInitialization() {
    // Assert
    assertNotNull(candidate.getSubmissions());
    assertTrue(candidate.getSubmissions().isEmpty());
  }

  @Test
  @DisplayName("Should have default tech stack")
  void testDefaultTechStack() {
    // Act
    Candidate defaultCandidate = Candidate.builder().build();

    // Assert
    assertEquals("JAVA", defaultCandidate.getTechStack());
  }
}
