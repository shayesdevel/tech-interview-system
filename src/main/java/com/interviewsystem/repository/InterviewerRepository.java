package com.interviewsystem.repository;

import com.interviewsystem.entity.Interviewer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Interviewer entity.
 *
 * <p>Provides CRUD operations and custom query methods for interviewer management.
 */
@Repository
public interface InterviewerRepository extends JpaRepository<Interviewer, Long> {

  /**
   * Find an interviewer by email address.
   *
   * @param email the email to search for
   * @return an Optional containing the interviewer if found
   */
  Optional<Interviewer> findByEmail(String email);

  /**
   * Find all active interviewers.
   *
   * @return a list of active interviewers
   */
  List<Interviewer> findByActiveTrue();

  /**
   * Find all inactive interviewers.
   *
   * @return a list of inactive interviewers
   */
  List<Interviewer> findByActiveFalse();

  /**
   * Find all interviewers with a specific role.
   *
   * @param role the interviewer role (e.g., "SENIOR_ENGINEER", "INTERVIEWER", "ADMIN")
   * @return a list of interviewers with the given role
   */
  List<Interviewer> findByRole(String role);

  /**
   * Find active interviewers with a specific role.
   *
   * @param role the interviewer role
   * @return a list of active interviewers with the given role
   */
  List<Interviewer> findByRoleAndActiveTrue(String role);

  /**
   * Check if an interviewer exists by email.
   *
   * @param email the email to check
   * @return true if interviewer exists, false otherwise
   */
  boolean existsByEmail(String email);

  /**
   * Count active interviewers.
   *
   * @return the count of active interviewers
   */
  long countByActiveTrue();
}
