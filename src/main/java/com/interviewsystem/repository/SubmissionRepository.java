package com.interviewsystem.repository;

import com.interviewsystem.entity.Submission;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Submission entity.
 *
 * <p>Provides CRUD operations and custom query methods for code submission management.
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

  /**
   * Find all submissions by a specific candidate.
   *
   * @param candidateId the ID of the candidate
   * @return a list of submissions from the candidate
   */
  List<Submission> findByCandidateId(Long candidateId);

  /**
   * Find all submissions for a specific problem.
   *
   * @param problemId the ID of the problem
   * @return a list of submissions for the problem
   */
  List<Submission> findByProblemId(Long problemId);

  /**
   * Find all submissions by a candidate for a specific problem.
   *
   * @param candidateId the ID of the candidate
   * @param problemId the ID of the problem
   * @return a list of submissions for the candidate-problem pair
   */
  List<Submission> findByCandidateIdAndProblemId(Long candidateId, Long problemId);

  /**
   * Find all submissions with a specific compilation status.
   *
   * @param compilationStatus the compilation status (e.g., "PENDING", "SUCCESS", "FAILED")
   * @return a list of submissions with the given status
   */
  List<Submission> findByCompilationStatus(String compilationStatus);

  /**
   * Find submissions submitted within a time range.
   *
   * @param startTime the start of the time range
   * @param endTime the end of the time range
   * @return a list of submissions within the time range
   */
  List<Submission> findBySubmittedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

  /**
   * Count submissions for a specific candidate.
   *
   * @param candidateId the ID of the candidate
   * @return the count of submissions by the candidate
   */
  long countByCandidateId(Long candidateId);

  /**
   * Count submissions for a specific problem.
   *
   * @param problemId the ID of the problem
   * @return the count of submissions for the problem
   */
  long countByProblemId(Long problemId);
}
