package org.springsteps.api.in.rest.v1.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Requires to provide at least email, or phone number value.
 *
 * <p>Because user identity is provided as:
 * {@code {email}}, {@code {phone number}}, {@code {email, phone number}},
 * they can't have both {@code null} value.
 *
 * <p>Custom validator.
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = RequireMinimalIdentityValidator.class)
@Documented
public @interface RequireMinimalIdentity {

  String message() default "User identity not provided. Provide user identity "
      + "be email, phone number, or both";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
