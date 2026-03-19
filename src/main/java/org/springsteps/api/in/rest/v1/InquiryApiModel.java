package org.springsteps.api.in.rest.v1;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;
import org.springsteps.api.in.rest.v1.validator.RequireMinimalIdentity;

/**
 * Inquiry REST API model.
 */
@Data
@JsonPropertyOrder({"id"})
@RequireMinimalIdentity
public class InquiryApiModel {

  /**
   * Inquiry.
   *
   * <p>UUID version 7.
   */
  private UUID id;

  @Email(message = "Invalid email address format.")
  @Size(min = 3, max = 2000, message = "Exceeded maximum length of the email.")
  private String email;

  @Size(min = 1, max = 1000,
      message = "Full name must contain at least 1 character and no more than 1 000 characters.")
  // TODO: 2026-03-17 TOKU: Custom regex? Blank string passed as valid.
  private String fullName;

  /**
   * Phone number.
   *
   * <p>There is not strictly defined format of the phone number.
   *
   * <p>Allowed characters:
   * <ul>
   *   <li><code>0-9</code></li>
   *   <li><code>a-z</code></li>
   *   <li><code>A-Z</code></li>
   *   <li><code>+</code></li>
   *   <li><code>#</code></li>
   *   <li>space</li>
   * </ul>
   */
  @Pattern(
      regexp = "^[a-zA-Z0-9+#][a-zA-Z0-9 +#]*$",
      message = "Invalid character in the phone number.")
  @Size(min = 1, max = 50, message = "Exceeded maximum length of the phone number.")
  private String phoneNumber;

  @Size(min = 2, max = 20000, message =
      "Inquiry text must contain at least 2 characters and no more than 20 000 characters.")
  // TODO: 2026-03-17 TOKU: Custom regex? Two empty character passed as valid.
  private String text;
}
