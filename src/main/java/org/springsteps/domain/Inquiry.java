package org.springsteps.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Inquiry.
 *
 * <p>Inquiry received from inquiry ingestion process.
 *
 * <p>This domain object is also the database entity for brevity,
 * because of the microservice architecture with only one domain object.
 */
@Table("inquiries")
@Data
public class Inquiry {

  /**
   * Inquiry.
   *
   * <p>UUID version 7.
   */
  @Id
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

  /**
   * Create a copy in inquiry.
   *
   * @param inquiry Inquiry to copy.
   * @return New instance in inquiry copy.
   */
  // TODO: 2026-03-18 TOKU: Write unit test.
  public static Inquiry from(final Inquiry inquiry) {
    var inquiryCopy = new Inquiry();

    inquiryCopy.setId(inquiry.getId());
    inquiryCopy.setEmail(inquiry.getEmail());
    inquiryCopy.setFullName(inquiry.getFullName());
    inquiryCopy.setPhoneNumber(inquiry.getPhoneNumber());
    inquiryCopy.setText(inquiry.getText());

    return inquiryCopy;
  }
}
