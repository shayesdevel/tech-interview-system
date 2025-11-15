package com.interviewsystem.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.interviewsystem.entity.Problem;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Repository CRUD tests for Problem entity.
 */
@DataJpaTest
@DisplayName("Problem Repository Tests")
class ProblemRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProblemRepository problemRepository;

  private Problem problem;

  @BeforeEach
  void setUp() {
    problem = Problem.builder()
        .title("Two Sum")
        .description("Find two numbers that add up to target")
        .difficulty("EASY")
        .category("ALGORITHMS")
        .primaryTechStack("JAVA")
        .estimatedMinutes(30)
        .maxScore(100)
        .build();
  }

  @Test
  @DisplayName("Should save problem and retrieve by ID")
  void testSaveAndRetrieveById() {
    // Act
    Problem saved = problemRepository.save(problem);
    entityManager.flush();

    Optional<Problem> retrieved = problemRepository.findById(saved.getId());

    // Assert
    assertTrue(retrieved.isPresent());
    assertEquals(problem.getTitle(), retrieved.get().getTitle());
    assertEquals(problem.getDifficulty(), retrieved.get().getDifficulty());
  }

  @Test
  @DisplayName("Should find problem by title")
  void testFindByTitle() {
    // Arrange
    problemRepository.save(problem);
    entityManager.flush();

    // Act
    Optional<Problem> found = problemRepository.findByTitle("Two Sum");

    // Assert
    assertTrue(found.isPresent());
    assertEquals("EASY", found.get().getDifficulty());
  }

  @Test
  @DisplayName("Should find all problems by difficulty")
  void testFindByDifficulty() {
    // Arrange
    Problem mediumProblem = Problem.builder()
        .title("Longest Substring")
        .description("Find longest substring...")
        .difficulty("MEDIUM")
        .category("ALGORITHMS")
        .primaryTechStack("JAVA")
        .build();
    problemRepository.save(problem);
    problemRepository.save(mediumProblem);
    entityManager.flush();

    // Act
    List<Problem> easyProblems = problemRepository.findByDifficulty("EASY");

    // Assert
    assertEquals(1, easyProblems.size());
    assertEquals("Two Sum", easyProblems.get(0).getTitle());
  }

  @Test
  @DisplayName("Should find all problems by category")
  void testFindByCategory() {
    // Arrange
    problemRepository.save(problem);
    Problem dataStructureProblem = Problem.builder()
        .title("Binary Tree Traversal")
        .description("Traverse a binary tree...")
        .difficulty("MEDIUM")
        .category("DATA_STRUCTURES")
        .primaryTechStack("JAVA")
        .build();
    problemRepository.save(dataStructureProblem);
    entityManager.flush();

    // Act
    List<Problem> algorithmProblems = problemRepository.findByCategory("ALGORITHMS");

    // Assert
    assertEquals(1, algorithmProblems.size());
    assertEquals("Two Sum", algorithmProblems.get(0).getTitle());
  }

  @Test
  @DisplayName("Should find all problems by primary tech stack")
  void testFindByPrimaryTechStack() {
    // Arrange
    Problem typeScriptProblem = Problem.builder()
        .title("React State Management")
        .description("Manage state in React...")
        .difficulty("MEDIUM")
        .category("OOP")
        .primaryTechStack("TYPESCRIPT")
        .build();
    problemRepository.save(problem);
    problemRepository.save(typeScriptProblem);
    entityManager.flush();

    // Act
    List<Problem> javaProblems = problemRepository.findByPrimaryTechStack("JAVA");

    // Assert
    assertEquals(1, javaProblems.size());
    assertEquals("Two Sum", javaProblems.get(0).getTitle());
  }

  @Test
  @DisplayName("Should find problems by difficulty and tech stack")
  void testFindByDifficultyAndPrimaryTechStack() {
    // Arrange
    Problem easyTypeScript = Problem.builder()
        .title("Hello World")
        .description("Print hello world")
        .difficulty("EASY")
        .category("ALGORITHMS")
        .primaryTechStack("TYPESCRIPT")
        .build();
    problemRepository.save(problem);
    problemRepository.save(easyTypeScript);
    entityManager.flush();

    // Act
    List<Problem> found = problemRepository
        .findByDifficultyAndPrimaryTechStack("EASY", "JAVA");

    // Assert
    assertEquals(1, found.size());
    assertEquals("Two Sum", found.get(0).getTitle());
  }

  @Test
  @DisplayName("Should check if problem exists by title")
  void testExistsByTitle() {
    // Arrange
    problemRepository.save(problem);
    entityManager.flush();

    // Act & Assert
    assertTrue(problemRepository.existsByTitle("Two Sum"));
    assertFalse(problemRepository.existsByTitle("Nonexistent Problem"));
  }
}
