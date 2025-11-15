package com.interviewsystem.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Entity mapping tests for Problem entity.
 */
@DisplayName("Problem Entity Tests")
class ProblemEntityTest {

  private Problem problem;

  @BeforeEach
  void setUp() {
    problem = new Problem();
  }

  @Test
  @DisplayName("Should create problem with all fields")
  void testCreateProblemWithAllFields() {
    // Arrange
    String title = "Longest Substring Without Repeating Characters";
    String description = "Find the longest substring without repeating characters...";
    String difficulty = "MEDIUM";
    String category = "ALGORITHMS";
    String primaryTechStack = "JAVA";

    // Act
    problem.setTitle(title);
    problem.setDescription(description);
    problem.setDifficulty(difficulty);
    problem.setCategory(category);
    problem.setPrimaryTechStack(primaryTechStack);

    // Assert
    assertEquals(title, problem.getTitle());
    assertEquals(description, problem.getDescription());
    assertEquals(difficulty, problem.getDifficulty());
    assertEquals(category, problem.getCategory());
    assertEquals(primaryTechStack, problem.getPrimaryTechStack());
  }

  @Test
  @DisplayName("Should have default values for optional fields")
  void testDefaultValues() {
    // Act
    Problem builtProblem = Problem.builder()
        .title("Test Problem")
        .description("Test description")
        .category("DATA_STRUCTURES")
        .primaryTechStack("JAVA")
        .build();

    // Assert
    assertEquals("MEDIUM", builtProblem.getDifficulty());
    assertEquals(60, builtProblem.getEstimatedMinutes());
    assertEquals(100, builtProblem.getMaxScore());
  }

  @Test
  @DisplayName("Should set timestamps on creation")
  void testTimestampsOnCreation() {
    // Act
    problem.onCreate();

    // Assert
    assertNotNull(problem.getCreatedAt());
    assertNotNull(problem.getUpdatedAt());
  }

  @Test
  @DisplayName("Should update timestamp on modification")
  void testTimestampOnUpdate() {
    // Arrange
    problem.onCreate();
    LocalDateTime originalUpdatedAt = problem.getUpdatedAt();

    // Act
    try {
      Thread.sleep(10);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    problem.onUpdate();

    // Assert
    assertNotEquals(originalUpdatedAt, problem.getUpdatedAt());
  }

  @Test
  @DisplayName("Should initialize testCases and submissions lists")
  void testCollectionsInitialization() {
    // Assert
    assertNotNull(problem.getTestCases());
    assertNotNull(problem.getSubmissions());
    assertTrue(problem.getTestCases().isEmpty());
    assertTrue(problem.getSubmissions().isEmpty());
  }

  @Test
  @DisplayName("Should build problem using builder")
  void testProblemBuilder() {
    // Act
    Problem builtProblem = Problem.builder()
        .title("Two Sum")
        .description("Given an array of integers...")
        .difficulty("EASY")
        .category("ALGORITHMS")
        .primaryTechStack("TYPESCRIPT")
        .estimatedMinutes(30)
        .maxScore(150)
        .build();

    // Assert
    assertEquals("Two Sum", builtProblem.getTitle());
    assertEquals("EASY", builtProblem.getDifficulty());
    assertEquals("TYPESCRIPT", builtProblem.getPrimaryTechStack());
    assertEquals(30, builtProblem.getEstimatedMinutes());
    assertEquals(150, builtProblem.getMaxScore());
  }
}
