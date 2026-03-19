package org.springsteps.api.in.rest.v1;

import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springsteps.domain.Inquiry;

/**
 * Map HTTP request/response to use cases service input/output.
 */
@Component
public class InquiryMapper {

  /**
   * Map {@link InquiryApiModel} REST API model to {@link Inquiry} domain entity.
   */
  @NotNull
  public Inquiry mapToInquiry(@NotNull final InquiryApiModel inquiryApiModel) {
    // TODO: 2026-03-19 TOKU: Unit tests.

    Inquiry inquiry = new Inquiry();

    inquiry.setId(inquiryApiModel.getId());
    inquiry.setEmail(inquiryApiModel.getEmail());
    inquiry.setFullName(inquiryApiModel.getFullName());
    inquiry.setPhoneNumber(inquiryApiModel.getPhoneNumber());
    inquiry.setText(inquiryApiModel.getText());

    return inquiry;
  }

  /**
   * Map {@link Inquiry} domain entity to {@link InquiryApiModel} REST API model.
   */
  @NotNull
  public InquiryApiModel mapToInquiryApiModel(@NotNull final Inquiry inquiry) {
    // TODO: 2026-03-19 TOKU: Unit tests.

    InquiryApiModel inquiryApiModel = new InquiryApiModel();

    inquiryApiModel.setId(inquiry.getId());
    inquiryApiModel.setEmail(inquiry.getEmail());
    inquiryApiModel.setFullName(inquiry.getFullName());
    inquiryApiModel.setPhoneNumber(inquiry.getPhoneNumber());
    inquiryApiModel.setText(inquiry.getText());

    return inquiryApiModel;
  }
}
