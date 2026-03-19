package org.springsteps.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springsteps.domain.Inquiry;

/**
 * {@link Inquiry} SQL repository implementation.
 */
@Repository
@Validated
public class InquiryRepository {

  private final InquirySpringJdbcDao inquirySpringDao;

  /**
   * Constructor.
   */
  public InquiryRepository(InquirySpringJdbcDao inquiryRepository) {
    this.inquirySpringDao = inquiryRepository;
  }

  /**
   * Create a new {@link Inquiry} entry.
   */
  @Transactional
  public @NotNull Inquiry save(@Valid @NotNull final Inquiry inquiry) {
    return inquirySpringDao.save(inquiry);
  }

  /**
   * Update an existing {@link Inquiry} entry.
   */
  @Transactional
  public @NotNull Inquiry update(@Valid @NotNull final Inquiry inquiry) {
    return inquirySpringDao.save(inquiry);
  }

  /**
   * Query all inquiries.
   */
  @Transactional
  public Iterable<Inquiry> findAll() {
    return inquirySpringDao.findAll();
  }

  /**
   * Query {@link Inquiry} by an email, phone number, or combination of both.
   *
   * <p>Inquiry matching order:
   * <ul>
   * <li>Entry with equal email, regardless of whether phone exist, or not.<br />
   * <em>If more than one entry is found, throws exception.</em></li>
   * <li>If email is not provided:
   * <ul>
   *   <li>Entry with equal phone number.<br/>
   *   <em>If more than one entry is found, throws exception.</em></li>
   * </ul>
   * </li>
   * </ul>
   */
  @Transactional
  public Optional<Inquiry> findByMatchIdentity(String email, String phoneNumber) {

    // TODO: 2026-03-19 TOKU: Unit tests.

    List<Inquiry> emailResults = Collections.emptyList();
    List<Inquiry> phoneNumberResults = Collections.emptyList();

    if (email != null) {
      emailResults = inquirySpringDao.findInquiryByEmail(email);
    }
    if (phoneNumber != null) {
      phoneNumberResults = inquirySpringDao.findInquiryByPhoneNumber(phoneNumber);
    }

    checkIdentityRedundancyOrThrow(emailResults, phoneNumberResults);

    if (!emailResults.isEmpty()) {
      return Optional.of(emailResults.getFirst());
    }

    if (!phoneNumberResults.isEmpty()) {
      return Optional.of(phoneNumberResults.getFirst());
    }

    return Optional.empty();
  }

  private void checkIdentityRedundancyOrThrow(List<Inquiry> emailResults,
                                              List<Inquiry> phoneNumberResults) {
    if ((emailResults.size() > 1)) {
      // TODO: 2026-03-18 TOKU: Dedicated exception: FewUsersWithTheSameIdentityException
      throw new RuntimeException("More users with the same identity (email) exist in database.");
    }

    if (phoneNumberResults.size() > 1) {
      // TODO: 2026-03-18 TOKU: Dedicated exception: FewUsersWithTheSameIdentityException
      throw new RuntimeException(
          "More users with the same identity (phone number) exist in database.");
    }
  }
}
