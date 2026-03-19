package org.springsteps.services.usecase;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springsteps.domain.Inquiry;
import org.springsteps.services.InquiryRepository;

/**
 * {@link ListInquiryUseCase} implementation.
 */
@Service
@Validated
public class ListInquiryUseCaseImplementation implements ListInquiryUseCase {

  private final InquiryRepository inquiryRepository;

  /**
   * Constructor.
   */
  public ListInquiryUseCaseImplementation(InquiryRepository inquiryService) {
    this.inquiryRepository = inquiryService;
  }

  @Override
  @NotNull
  public Iterable<Inquiry> listAll() {
    return inquiryRepository.findAll();
  }
}
