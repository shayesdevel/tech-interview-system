package com.interviewsystem.repository;

import com.interviewsystem.entity.Problem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Problem entity.
 *
 * <p>Provides CRUD operations and custom query methods for interview problem management.
 */
@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

  /**
   * Find a problem by title.
   *
   * @param title the problem title to search for
   * @return an Optional containing the problem if found
   */
  Optional<Problem> findByTitle(String title);

  /**
   * Find all problems with a specific difficulty level.
   *
   * @param difficulty the difficulty level (e.g., "EASY", "MEDIUM", "HARD")
   * @return a list of problems with matching difficulty
   */
  List<Problem> findByDifficulty(String difficulty);

  /**
   * Find all problems with a specific category.
   *
   * @param category the problem category
   * @return a list of problems in the specified category
   */
  List<Problem> findByCategory(String category);

  /**
   * Find all problems for a specific technology stack.
   *
   * @param primaryTechStack the technology stack
   * @return a list of problems for the given tech stack
   */
  List<Problem> findByPrimaryTechStack(String primaryTechStack);

  /**
   * Find problems by difficulty and technology stack.
   *
   * @param difficulty the difficulty level
   * @param primaryTechStack the technology stack
   * @return a list of problems matching both criteria
   */
  List<Problem> findByDifficultyAndPrimaryTechStack(String difficulty, String primaryTechStack);

  /**
   * Check if a problem exists by title.
   *
   * @param title the problem title
   * @return true if problem exists, false otherwise
   */
  boolean existsByTitle(String title);
}
