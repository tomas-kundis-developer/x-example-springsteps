package org.springsteps.api.in.rest.v1.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springsteps.api.in.rest.v1.InquiryApiModel;

public class RequireMinimalIdentityValidator
    implements ConstraintValidator<RequireMinimalIdentity, InquiryApiModel> {

  @Override
  public boolean isValid(InquiryApiModel inquiryApiModel, ConstraintValidatorContext constraintContext) {
    if (inquiryApiModel == null) {
      return true;
    }

    return !StringUtils.isAllBlank(
        inquiryApiModel.getEmail(),
        inquiryApiModel.getPhoneNumber());
  }
}
