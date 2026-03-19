package org.springsteps.services.usecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springsteps.domain.Inquiry;
import org.springsteps.domain.InquiryFactory;
import org.springsteps.services.InquiryRepository;

/**
 * {@link SubmitInquiryUseCase} implementation.
 */
@Service
@Validated
public class SubmitInquiryUseCaseImplementation implements SubmitInquiryUseCase {

  private final InquiryRepository inquiryRepository;

  /**
   * Constructor.
   */
  public SubmitInquiryUseCaseImplementation(InquiryRepository inquiryService) {
    this.inquiryRepository = inquiryService;
  }

  /**
   * Submit an {@link Inquiry}.
   */
  @Override
  public @NotNull Inquiry submitInquiry(@Valid @NotNull Inquiry inquiry) {

    // TODO: 2026-03-18 TOKU: Integration tests.

    validateInquiry(inquiry);

    Optional<Inquiry> matchedIdentitiesResults =
        inquiryRepository.findByMatchIdentity(inquiry.getEmail(), inquiry.getPhoneNumber());

    AtomicReference<Inquiry> submittedInquiry = new AtomicReference<>();

    matchedIdentitiesResults.ifPresentOrElse(
        // Matched identity - update existing user's inquiry entry.
        // Don't erase existing identity fields {email, phoneNumber} if they always exist.
        originInquiry ->
            submittedInquiry.set(
                inquiryRepository.update(
                    InquiryFactory.createNewInquiryWithUpdatedData(originInquiry, inquiry))),

        // No matched identity - create a new user (new inquiry entry).
        () -> submittedInquiry.set(inquiryRepository.save(inquiry))
    );

    return submittedInquiry.get();
  }

  /**
   * Complex and logical validation for further processing of {@link Inquiry} domain object.
   */
  private void validateInquiry(final Inquiry inquiry) {

    if (StringUtils.isBlank(inquiry.getEmail()) && StringUtils.isBlank(inquiry.getPhoneNumber())) {
      // TODO: 2026-03-18 TOKU: Dedicated exception.
      throw new RuntimeException("Inquiry minimum data validation violated.");
    }
  }
}
