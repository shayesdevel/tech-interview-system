package com.interviewsystem;

import static org.junit.jupiter.api.Assertions.*;

import com.interviewsystem.entity.Candidate;
import com.interviewsystem.entity.Evaluation;
import com.interviewsystem.entity.Interviewer;
import com.interviewsystem.entity.Problem;
import com.interviewsystem.entity.Submission;
import com.interviewsystem.entity.TestCase;
import com.interviewsystem.repository.CandidateRepository;
import com.interviewsystem.repository.EvaluationRepository;
import com.interviewsystem.repository.InterviewerRepository;
import com.interviewsystem.repository.ProblemRepository;
import com.interviewsystem.repository.SubmissionRepository;
import com.interviewsystem.repository.TestCaseRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Integration tests to verify database schema creation and relationships.
 */
@DataJpaTest
@DisplayName("Database Migration Integration Tests")
class MigrationTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private ProblemRepository problemRepository;

  @Autowired
  private TestCaseRepository testCaseRepository;

  @Autowired
  private SubmissionRepository submissionRepository;

  @Autowired
  private EvaluationRepository evaluationRepository;

  @Autowired
  private InterviewerRepository interviewerRepository;

  @Test
  @DisplayName("Should create complete data flow: Candidate -> Submission -> Evaluation")
  void testCompleteDataFlow() {
    // Arrange: Create candidate
    Candidate candidate = Candidate.builder()
        .email("test.candidate@example.com")
        .firstName("Test")
        .lastName("Candidate")
        .techStack("JAVA")
        .build();
    candidateRepository.save(candidate);
    entityManager.flush();

    // Create problem with test cases
    Problem problem = Problem.builder()
        .title("Sum of Two Numbers")
        .description("Add two numbers and return the result")
        .difficulty("EASY")
        .category("ALGORITHMS")
        .primaryTechStack("JAVA")
        .estimatedMinutes(30)
        .maxScore(100)
        .build();
    problemRepository.save(problem);
    entityManager.flush();

    TestCase testCase = TestCase.builder()
        .problem(problem)
        .inputDescription("2, 3")
        .expectedOutput("5")
        .weightPercentage(100)
        .isHidden(false)
        .timeoutSeconds(5)
        .build();
    testCaseRepository.save(testCase);
    entityManager.flush();

    // Create submission
    Submission submission = Submission.builder()
        .candidate(candidate)
        .problem(problem)
        .codeContent("public int sum(int a, int b) { return a + b; }")
        .language("JAVA")
        .submittedAt(LocalDateTime.now())
        .compilationStatus("SUCCESS")
        .build();
    submissionRepository.save(submission);
    entityManager.flush();

    // Create evaluation
    Evaluation evaluation = Evaluation.builder()
        .submission(submission)
        .testPassCount(1)
        .totalTestCases(1)
        .executionScore(100)
        .codeQualityScore(90)
        .overallScore(95)
        .feedback("Great solution!")
        .status("COMPLETED")
        .evaluatedAt(LocalDateTime.now())
        .build();
    evaluationRepository.save(evaluation);
    entityManager.flush();
    entityManager.clear();

    // Act & Assert: Verify complete flow
    Candidate retrievedCandidate = candidateRepository.findByEmail("test.candidate@example.com")
        .orElseThrow();
    assertEquals("Test", retrievedCandidate.getFirstName());

    Problem retrievedProblem = problemRepository.findByTitle("Sum of Two Numbers")
        .orElseThrow();
    assertEquals("EASY", retrievedProblem.getDifficulty());

    java.util.List<TestCase> testCases = testCaseRepository.findByProblemId(retrievedProblem.getId());
    assertEquals(1, testCases.size());
    assertEquals("5", testCases.get(0).getExpectedOutput());

    java.util.List<Submission> submissions = submissionRepository
        .findByCandidateIdAndProblemId(retrievedCandidate.getId(), retrievedProblem.getId());
    assertEquals(1, submissions.size());

    Evaluation retrievedEvaluation = evaluationRepository
        .findBySubmissionId(submissions.get(0).getId()).orElseThrow();
    assertEquals(95, retrievedEvaluation.getOverallScore());
    assertEquals("COMPLETED", retrievedEvaluation.getStatus());
  }

  @Test
  @DisplayName("Should support interviewer assignment to evaluation")
  void testInterviewerAssignmentToEvaluation() {
    // Arrange
    Interviewer interviewer = Interviewer.builder()
        .email("interviewer@example.com")
        .firstName("Senior")
        .lastName("Engineer")
        .role("SENIOR_ENGINEER")
        .expertiseAreas("JAVA,SPRING,SYSTEM_DESIGN")
        .active(true)
        .build();
    interviewerRepository.save(interviewer);
    entityManager.flush();

    Candidate candidate = Candidate.builder()
        .email("candidate@example.com")
        .firstName("John")
        .lastName("Doe")
        .techStack("JAVA")
        .build();
    candidateRepository.save(candidate);

    Problem problem = Problem.builder()
        .title("Design Pattern")
        .description("Implement a design pattern")
        .difficulty("HARD")
        .category("OOP")
        .primaryTechStack("JAVA")
        .build();
    problemRepository.save(problem);
    entityManager.flush();

    Submission submission = Submission.builder()
        .candidate(candidate)
        .problem(problem)
        .codeContent("public class Singleton { ... }")
        .language("JAVA")
        .submittedAt(LocalDateTime.now())
        .compilationStatus("SUCCESS")
        .build();
    submissionRepository.save(submission);
    entityManager.flush();

    Evaluation evaluation = Evaluation.builder()
        .submission(submission)
        .interviewer(interviewer)
        .testPassCount(5)
        .totalTestCases(5)
        .executionScore(100)
        .codeQualityScore(85)
        .overallScore(92)
        .feedback("Well-designed implementation")
        .status("COMPLETED")
        .evaluatedAt(LocalDateTime.now())
        .build();
    evaluationRepository.save(evaluation);
    entityManager.flush();
    entityManager.clear();

    // Act
    Evaluation retrieved = evaluationRepository.findAll().get(0);

    // Assert
    assertNotNull(retrieved.getInterviewer());
    assertEquals("interviewer@example.com", retrieved.getInterviewer().getEmail());
    assertEquals("SENIOR_ENGINEER", retrieved.getInterviewer().getRole());
  }

  @Test
  @DisplayName("Should enforce unique constraints")
  void testUniqueConstraints() {
    // Arrange
    Candidate candidate1 = Candidate.builder()
        .email("duplicate@example.com")
        .firstName("John")
        .lastName("Doe")
        .techStack("JAVA")
        .build();
    candidateRepository.save(candidate1);
    entityManager.flush();

    // Act & Assert: Attempt to create duplicate email
    Candidate candidate2 = Candidate.builder()
        .email("duplicate@example.com")
        .firstName("Jane")
        .lastName("Smith")
        .techStack("JAVA")
        .build();
    candidateRepository.save(candidate2);

    assertThrows(Exception.class, entityManager::flush,
        "Should throw constraint violation for duplicate email");
  }

  @Test
  @DisplayName("Should maintain timestamps correctly")
  void testTimestampBehavior() {
    // Arrange & Act
    Candidate candidate = Candidate.builder()
        .email("timestamp.test@example.com")
        .firstName("Test")
        .lastName("User")
        .techStack("JAVA")
        .build();
    Candidate saved = candidateRepository.save(candidate);
    entityManager.flush();

    LocalDateTime createdAt = saved.getCreatedAt();
    LocalDateTime updatedAt = saved.getUpdatedAt();

    // Wait a bit and update
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    saved.setFirstName("Updated");
    candidateRepository.save(saved);
    entityManager.flush();
    entityManager.clear();

    Candidate updated = candidateRepository.findById(saved.getId()).orElseThrow();

    // Assert
    assertEquals(createdAt, updated.getCreatedAt(),
        "Created at should not change");
    assertTrue(updated.getUpdatedAt().isAfter(updatedAt),
        "Updated at should be newer after modification");
  }
}
