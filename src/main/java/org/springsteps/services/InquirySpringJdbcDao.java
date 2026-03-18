package org.springsteps.services;

import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springsteps.domain.Inquiry;

/**
 * {@link Inquiry} DAO for SQL database.
 *
 * <p>Implemented with Spring Data JDBC automatically generated repository.
 */
@Repository
public interface InquirySpringJdbcDao extends CrudRepository<Inquiry, UUID> {

  /**
   * Find {@link Inquiry} by email.
   */
  // TODO: 2026-03-19 TOKU: Unit tests.
  List<Inquiry> findInquiryByEmail(String email);

  /**
   * Find {@link Inquiry} by phone number.
   */
  // TODO: 2026-03-19 TOKU: Unit tests.
  List<Inquiry> findInquiryByPhoneNumber(String phoneNumber);

  /**
   * Find {@link Inquiry} by combination of email and phone number.
   *
   * @param email       Can be null.
   * @param phoneNumber Can be null.
   */
  List<Inquiry> findInquiryByEmailAndPhoneNumber(String email, String phoneNumber);
}
