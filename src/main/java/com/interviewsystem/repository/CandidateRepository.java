package com.interviewsystem.repository;

import com.interviewsystem.entity.Candidate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Candidate entity.
 *
 * <p>Provides CRUD operations and custom query methods for candidate data management.
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

  /**
   * Find a candidate by email address.
   *
   * @param email the email to search for
   * @return an Optional containing the candidate if found
   */
  Optional<Candidate> findByEmail(String email);

  /**
   * Find all candidates with a specific technology stack.
   *
   * @param techStack the technology stack to filter by
   * @return a list of candidates matching the tech stack
   */
  List<Candidate> findByTechStack(String techStack);

  /**
   * Check if a candidate exists by email address.
   *
   * @param email the email to check
   * @return true if candidate exists, false otherwise
   */
  boolean existsByEmail(String email);
}
