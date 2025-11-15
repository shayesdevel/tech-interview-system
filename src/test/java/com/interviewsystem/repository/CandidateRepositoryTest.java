package com.interviewsystem.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.interviewsystem.entity.Candidate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 * Repository CRUD tests for Candidate entity.
 */
@DataJpaTest
@DisplayName("Candidate Repository Tests")
class CandidateRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private CandidateRepository candidateRepository;

  private Candidate candidate;

  @BeforeEach
  void setUp() {
    candidate = Candidate.builder()
        .email("john.doe@example.com")
        .firstName("John")
        .lastName("Doe")
        .phoneNumber("123-456-7890")
        .techStack("JAVA")
        .build();
  }

  @Test
  @DisplayName("Should save candidate and retrieve by ID")
  void testSaveAndRetrieveById() {
    // Act
    Candidate saved = candidateRepository.save(candidate);
    entityManager.flush();

    Optional<Candidate> retrieved = candidateRepository.findById(saved.getId());

    // Assert
    assertTrue(retrieved.isPresent());
    assertEquals(candidate.getEmail(), retrieved.get().getEmail());
    assertEquals(candidate.getFirstName(), retrieved.get().getFirstName());
  }

  @Test
  @DisplayName("Should find candidate by email")
  void testFindByEmail() {
    // Arrange
    candidateRepository.save(candidate);
    entityManager.flush();

    // Act
    Optional<Candidate> found = candidateRepository.findByEmail("john.doe@example.com");

    // Assert
    assertTrue(found.isPresent());
    assertEquals("John", found.get().getFirstName());
  }

  @Test
  @DisplayName("Should find all candidates by tech stack")
  void testFindByTechStack() {
    // Arrange
    Candidate candidate1 = Candidate.builder()
        .email("john@example.com")
        .firstName("John")
        .lastName("Doe")
        .techStack("JAVA")
        .build();
    Candidate candidate2 = Candidate.builder()
        .email("jane@example.com")
        .firstName("Jane")
        .lastName("Doe")
        .techStack("TYPESCRIPT")
        .build();
    candidateRepository.save(candidate1);
    candidateRepository.save(candidate2);
    entityManager.flush();

    // Act
    List<Candidate> javaCandidates = candidateRepository.findByTechStack("JAVA");

    // Assert
    assertEquals(1, javaCandidates.size());
    assertEquals("john@example.com", javaCandidates.get(0).getEmail());
  }

  @Test
  @DisplayName("Should check if candidate exists by email")
  void testExistsByEmail() {
    // Arrange
    candidateRepository.save(candidate);
    entityManager.flush();

    // Act & Assert
    assertTrue(candidateRepository.existsByEmail("john.doe@example.com"));
    assertFalse(candidateRepository.existsByEmail("nonexistent@example.com"));
  }

  @Test
  @DisplayName("Should update candidate")
  void testUpdateCandidate() {
    // Arrange
    Candidate saved = candidateRepository.save(candidate);
    entityManager.flush();
    entityManager.clear();

    // Act
    saved.setFirstName("Jane");
    saved.setPhoneNumber("987-654-3210");
    candidateRepository.save(saved);
    entityManager.flush();

    Optional<Candidate> updated = candidateRepository.findById(saved.getId());

    // Assert
    assertTrue(updated.isPresent());
    assertEquals("Jane", updated.get().getFirstName());
    assertEquals("987-654-3210", updated.get().getPhoneNumber());
  }

  @Test
  @DisplayName("Should delete candidate")
  void testDeleteCandidate() {
    // Arrange
    Candidate saved = candidateRepository.save(candidate);
    entityManager.flush();
    Long id = saved.getId();

    // Act
    candidateRepository.deleteById(id);
    entityManager.flush();

    // Assert
    assertTrue(candidateRepository.findById(id).isEmpty());
  }

  @Test
  @DisplayName("Should count all candidates")
  void testCountAllCandidates() {
    // Arrange
    candidateRepository.save(candidate);
    Candidate candidate2 = Candidate.builder()
        .email("jane@example.com")
        .firstName("Jane")
        .lastName("Smith")
        .techStack("JAVA")
        .build();
    candidateRepository.save(candidate2);
    entityManager.flush();

    // Act
    long count = candidateRepository.count();

    // Assert
    assertEquals(2, count);
  }
}
