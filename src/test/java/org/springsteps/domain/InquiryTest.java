package org.springsteps.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InquiryTest {

  @Autowired
  private Validator validator;

  @Test
  void validateEmailTest() {
    var propertyName = "email";
    var errorMessage = "Invalid email address format.";
    var errorMessageSize = "Text size out of the mininum/maximum length boundaries.";

    // PASS: Null

    var inquiry1 = new Inquiry();

    assertThat(inquiry1.getEmail()).isNull();

    Set<ConstraintViolation<Inquiry>> violations1 =
        validator.validateProperty(inquiry1, propertyName);

    assertThat(violations1).isEmpty();

    // FAIL: Empty string

    var inquiry2 = new Inquiry();
    inquiry2.setEmail("");

    Set<ConstraintViolation<Inquiry>> violations2 =
        validator.validateProperty(inquiry2, propertyName);

    assertThat(violations2).hasSize(1);
    assertThat(violations2.stream().findFirst().get().getMessage()).isEqualTo(errorMessageSize);

    // FAIL: Blank string

    var inquiry3 = new Inquiry();
    inquiry3.setEmail("   ");

    Set<ConstraintViolation<Inquiry>> violations3 =
        validator.validateProperty(inquiry3, propertyName);

    assertThat(violations3).hasSize(1);
    assertThat(violations3.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // FAIL: 1 character, invalid email format

    var inquiry4 = new Inquiry();
    inquiry4.setEmail("a");

    Set<ConstraintViolation<Inquiry>> violations4 =
        validator.validateProperty(inquiry4, propertyName);

    assertThat(violations4).hasSize(2);

    var violations4ErrorsStr = violations4.stream()
        .map(ConstraintViolation::getMessage)
        .toList();

    assertThat(violations4ErrorsStr).containsExactlyInAnyOrder(errorMessage, errorMessageSize);

    // PASS: intranet valid email format

    var inquiry5 = new Inquiry();
    inquiry5.setEmail("foo@bar");

    Set<ConstraintViolation<Inquiry>> violations5 =
        validator.validateProperty(inquiry5, propertyName);

    assertThat(violations5).isEmpty();

    // PASS: valid email format

    var inquiry6 = new Inquiry();
    inquiry6.setEmail("foo@bar.xyz");

    Set<ConstraintViolation<Inquiry>> violations6 =
        validator.validateProperty(inquiry6, propertyName);

    assertThat(violations6).isEmpty();
  }

  @Test
  void validateFullNameTest() {
    var propertyName = "fullName";

    // PASS: Null

    var inquiry1 = new Inquiry();

    assertThat(inquiry1.getFullName()).isNull();

    Set<ConstraintViolation<Inquiry>> violations1 =
        validator.validateProperty(inquiry1, propertyName);

    assertThat(violations1).isEmpty();

    // FAIL: Empty string

    var inquiry2 = new Inquiry();
    inquiry2.setFullName("");

    Set<ConstraintViolation<Inquiry>> violations2 =
        validator.validateProperty(inquiry2, propertyName);

    assertThat(violations2).hasSize(1);
    assertThat(violations2.stream().findFirst().get().getMessage()).isEqualTo(
        "Full name must contain at least 1 character and no more than 1 000 characters.");

    // PASS: Blank string

    var inquiry3 = new Inquiry();
    inquiry3.setFullName(" ");

    Set<ConstraintViolation<Inquiry>> violations3 =
        validator.validateProperty(inquiry3, propertyName);

    assertThat(violations3).isEmpty();

    // PASS: 1 character

    var inquiry4 = new Inquiry();
    inquiry4.setFullName("a");

    Set<ConstraintViolation<Inquiry>> violations4 =
        validator.validateProperty(inquiry4, propertyName);

    assertThat(violations4).isEmpty();
  }

  @Test
  void validatePhoneNumberPatternTest() {
    var propertyName = "phoneNumber";
    var errorMessage = "Invalid character in the phone number.";

    // PASS: Null

    var inquiry1 = new Inquiry();

    assertThat(inquiry1.getPhoneNumber()).isNull();

    Set<ConstraintViolation<Inquiry>> violations1 =
        validator.validateProperty(inquiry1, propertyName);

    assertThat(violations1).isEmpty();

    // PASS: All allowed characters.

    var inquiry2 = new Inquiry();
    inquiry2.setPhoneNumber("+421 0905 111 #a");

    Set<ConstraintViolation<Inquiry>> violations2 =
        validator.validateProperty(inquiry2, propertyName);

    assertThat(violations2).isEmpty();

    // FAIL: Only blank characters, correct size

    var inquiry3 = new Inquiry();
    inquiry3.setPhoneNumber("  ");

    Set<ConstraintViolation<Inquiry>> violations3 =
        validator.validateProperty(inquiry3, propertyName);

    assertThat(violations3).hasSize(1);
    assertThat(violations3.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // FAIL: Blank first character, correct phone number.

    var inquiry4 = new Inquiry();
    inquiry4.setPhoneNumber(" 0905 111 222");

    Set<ConstraintViolation<Inquiry>> violations4 =
        validator.validateProperty(inquiry4, propertyName);

    assertThat(violations4).hasSize(1);
    assertThat(violations4.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // FAIL: Not allowed character

    var inquiry5 = new Inquiry();
    inquiry5.setPhoneNumber("0905-111-222");

    Set<ConstraintViolation<Inquiry>> violations5 =
        validator.validateProperty(inquiry5, propertyName);

    assertThat(violations5).hasSize(1);
    assertThat(violations5.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);
  }

  @Test
  void validateTextTest() {
    var propertyName = "text";
    var errorMessage =
        "Inquiry text must contain at least 2 characters and no more than 20 000 characters.";

    // PASS: Null

    var inquiry1 = new Inquiry();

    assertThat(inquiry1.getText()).isNull();

    Set<ConstraintViolation<Inquiry>> violations1 =
        validator.validateProperty(inquiry1, propertyName);

    assertThat(violations1).isEmpty();

    // FAIL: Empty string

    var inquiry2 = new Inquiry();
    inquiry2.setText("");

    Set<ConstraintViolation<Inquiry>> violations2 =
        validator.validateProperty(inquiry2, propertyName);

    assertThat(violations2).hasSize(1);
    assertThat(violations2.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // FAIL: Blank string, 1 character

    var inquiry3 = new Inquiry();
    inquiry3.setText(" ");

    Set<ConstraintViolation<Inquiry>> violations3 =
        validator.validateProperty(inquiry3, propertyName);

    assertThat(violations3).hasSize(1);
    assertThat(violations3.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // PASS: Blank string, 2 characters

    var inquiry4 = new Inquiry();
    inquiry4.setEmail("  ");

    Set<ConstraintViolation<Inquiry>> violations4 =
        validator.validateProperty(inquiry4, propertyName);

    assertThat(violations4).isEmpty();

    // FAIL: 1 character

    var inquiry5 = new Inquiry();
    inquiry5.setText("a");

    Set<ConstraintViolation<Inquiry>> violations5 =
        validator.validateProperty(inquiry5, propertyName);

    assertThat(violations5).hasSize(1);
    assertThat(violations5.stream().findFirst().get().getMessage()).isEqualTo(errorMessage);

    // PASS: 2 characters

    var inquiry6 = new Inquiry();
    inquiry6.setText("aa");

    Set<ConstraintViolation<Inquiry>> violations6 =
        validator.validateProperty(inquiry6, propertyName);

    assertThat(violations6).isEmpty();
  }
}
