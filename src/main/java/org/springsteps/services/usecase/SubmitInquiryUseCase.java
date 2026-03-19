package org.springsteps.services.usecase;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springsteps.domain.Inquiry;

/**
 * Submit {@link Inquiry} use case.
 *
 * <p><b>Feature:</b> Submit an inquiry.
 * <ul>
 *   <li>If user with the same email or phone number already exist,
 *   the new inquiry must be associated with that existing user.</li>
 *   <li>If no match is found, a new user should be created.</li>
 * </ul>
 *
 * <p><b>Pre-Conditions:</b> An inquiry must contain at least an email or a phone number.</p>
 */
public interface SubmitInquiryUseCase {

  /**
   * Submit an {@link Inquiry}.
   */
  @NotNull
  Inquiry submitInquiry(@Valid @NotNull Inquiry inquiry);
}
