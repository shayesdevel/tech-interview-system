package com.interviewsystem.repository;

import com.interviewsystem.entity.TestCase;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for TestCase entity.
 *
 * <p>Provides CRUD operations and custom query methods for test case management.
 */
@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {

  /**
   * Find all test cases for a specific problem.
   *
   * @param problemId the ID of the problem
   * @return a list of test cases for the problem
   */
  List<TestCase> findByProblemId(Long problemId);

  /**
   * Find all visible test cases for a problem (not hidden from candidates).
   *
   * @param problemId the ID of the problem
   * @param isHidden whether the test case is hidden
   * @return a list of visible test cases
   */
  List<TestCase> findByProblemIdAndIsHidden(Long problemId, Boolean isHidden);

  /**
   * Count test cases for a specific problem.
   *
   * @param problemId the ID of the problem
   * @return the count of test cases for the problem
   */
  long countByProblemId(Long problemId);
}
