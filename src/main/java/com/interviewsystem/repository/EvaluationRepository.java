package com.interviewsystem.repository;

import com.interviewsystem.entity.Evaluation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Evaluation entity.
 *
 * <p>Provides CRUD operations and custom query methods for evaluation management.
 */
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

  /**
   * Find an evaluation by submission ID.
   *
   * @param submissionId the ID of the submission
   * @return an Optional containing the evaluation if found
   */
  Optional<Evaluation> findBySubmissionId(Long submissionId);

  /**
   * Find all evaluations by a specific interviewer.
   *
   * @param interviewerId the ID of the interviewer
   * @return a list of evaluations performed by the interviewer
   */
  List<Evaluation> findByInterviewerId(Long interviewerId);

  /**
   * Find all evaluations with a specific status.
   *
   * @param status the evaluation status (e.g., "PENDING", "IN_REVIEW", "COMPLETED")
   * @return a list of evaluations with the given status
   */
  List<Evaluation> findByStatus(String status);

  /**
   * Find all pending evaluations (status = "PENDING").
   *
   * @return a list of pending evaluations
   */
  List<Evaluation> findByStatusOrderByCreatedAtAsc(String status);

  /**
   * Find evaluations completed within a time range.
   *
   * @param startTime the start of the time range
   * @param endTime the end of the time range
   * @return a list of completed evaluations within the time range
   */
  List<Evaluation> findByEvaluatedAtBetween(LocalDateTime startTime, LocalDateTime endTime);

  /**
   * Count pending evaluations.
   *
   * @return the count of pending evaluations
   */
  long countByStatus(String status);

  /**
   * Count evaluations performed by a specific interviewer.
   *
   * @param interviewerId the ID of the interviewer
   * @return the count of evaluations by the interviewer
   */
  long countByInterviewerId(Long interviewerId);
}
