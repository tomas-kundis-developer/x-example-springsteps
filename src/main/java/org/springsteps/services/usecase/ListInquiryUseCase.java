package org.springsteps.services.usecase;

import jakarta.validation.constraints.NotNull;
import org.springsteps.domain.Inquiry;

/**
 * Use case services for various listings of {@link org.springsteps.domain.Inquiry}.
 */
public interface ListInquiryUseCase {

  /**
   * <b>Feature:</b> List all {@link Inquiry} entries.
   */
  @NotNull
  Iterable<Inquiry> listAll();
}
