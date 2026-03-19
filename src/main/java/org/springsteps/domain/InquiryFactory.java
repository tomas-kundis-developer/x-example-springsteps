package org.springsteps.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * Entity domain factory.
 */
@UtilityClass
public class InquiryFactory {

  /**
   * Create a new instance of updated {@code originInquiry}
   * with fresh (more actual) data from the {@code freshDataInquiry}.
   *
   * <p>{@code originInquiry} and {@code freshDataInquiry} stay unmodified.
   *
   * <ul>
   *   <li>Preserve the id in the original object.</li>
   *   <li>Do not update existing identity fields {email, phoneNumber}
   *   with a {@code null} value if they already exist.</li>
   * </ul>
   */
  public Inquiry createNewInquiryWithUpdatedData(@Valid @NotNull final Inquiry originInquiry,
                                                 @Valid @NotNull final Inquiry freshDataInquiry) {

    // TODO: 2026-03-19 TOKU: Unit tests.

    var updatedInquiry = Inquiry.from(originInquiry);

    // Do not update existing identity fields {email, phoneNumber} with a null value
    // if they already exist.

    if (StringUtils.isNotBlank(freshDataInquiry.getEmail())) {
      updatedInquiry.setEmail(freshDataInquiry.getEmail());
    }
    if (StringUtils.isNotBlank(freshDataInquiry.getPhoneNumber())) {
      updatedInquiry.setPhoneNumber(freshDataInquiry.getPhoneNumber());
    }

    // Copy other fields, even if they don't contain values.
    updatedInquiry.setFullName(freshDataInquiry.getFullName());
    updatedInquiry.setText(freshDataInquiry.getText());

    return updatedInquiry;
  }
}
