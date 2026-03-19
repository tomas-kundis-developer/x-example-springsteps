package org.springsteps.services.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import jakarta.validation.ConstraintViolationException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springsteps.domain.Inquiry;
import org.springsteps.services.InquirySpringJdbcDao;

@SpringBootTest
class SubmitInquiryUseCaseImplementationIT {

  @Autowired
  InquirySpringJdbcDao inquirySpringJdbcDao;

  @Autowired
  private SubmitInquiryUseCaseImplementation submitInquiryUseCase;

  /**
   * Submit inquiry without an email and phone number.
   */
  @Test
  void testSubmitInquiry_acceptanceTestWithoutUserIdentity() {
    var inquiry = new Inquiry();
    inquiry.setEmail(null);
    inquiry.setPhoneNumber(null);
    inquiry.setText("Test inquiry #1002.");

    assertThatExceptionOfType(RuntimeException.class)
        .isThrownBy(() -> submitInquiryUseCase.submitInquiry(inquiry))
        .withNoCause()
        .withMessageContaining("Inquiry minimum data validation violated.");
  }

  /**
   * Basic acceptance test scenario for this use case.
   *
   * <p>WIP (Work In Progress).
   *
   * <p>Idea: We are always updating the same inquiry entry,
   * changing and combining new submit sequences with all possible user identity combinations:
   * {email}, {phone}, {email, phone}.
   *
   * <p><hr/>
   *
   * <p><b>BDD TEST STYLE TEMPLATE:</b><br/>
   *
   * <p><b>Feature:</b> Submit an inquiry.
   *
   * <p><b>Scenario:</b> Submit sequentially multiple inquiries as the same user.
   *
   * <p><b>Given (Pre-Conditions):</b> Inquiry with associated email, or phone number doesn't exist.
   *
   * <p><b>Post-Conditions:</b>
   * <ul>
   *   <li>Only one inquiry with associated email and phone number exist.</li>
   *   <li>Only the last inquiry submit version is saved.</li>
   *   <li>Both email and phone number are always preserved,
   *   regardless which one was missing in last submit.</li>
   * </ul>
   *
   * <p><b>When:</b> I provide inquiry 1 for submit,
   * user is identified only by {email=foo@bar}.<br/>
   * Inquiry 1 has <b>no id generated yet</b>.<br/>
   * {@code {id=null; email=foo@bar; text=inquiry1}}
   *
   * <p><b>Then:</b> Only this one inquiry entry for given user {email=foo@bar} exist.<br/>
   * Inquiry 1 is persisted with <b>newly generated id</b>.<br/>
   * {@code {id=019...3ca; email=foo@bar; text=inquiry-1}}
   *
   * <p><b>When:</b> I provide inquiry 2 for submit,
   * with existing user {email, phoneNumber} from the inquiry 1.<br/>
   * {@code {id=null; email=foo@bar; phoneNumber=44111; text=inquiry1-rev2}}
   *
   * <p><b>Then:</b> Identity matched (by email). Updated existing inquiry: phone number, text.<br/>
   * {@code {id=id=019...3ca; email=foo@bar; phoneNumber=44111; text=inquiry1-rev2}}
   *
   * <p><b>When:</b> I provide inquiry 3 for submit,
   * with existing user {email, phoneNumber} from the inquiry 1.<br/>
   * {@code {id=null; email=foo@bar; phoneNumber=44111; text=inquiry1-rev3}}
   *
   * <p><b>Then:</b> Identity matched (by email). Updated existing inquiry: text.<br/>
   * {@code {id=id=019...3ca; email=foo@bar; phoneNumber=44111; text=inquiry1-rev3}}
   *
   * <p><b>When:</b> I provide inquiry 4 for submit, with existing user {email} from the inquiry 1.
   * {@code {id=null; email=foo@bar; text=inquiry1-rev4}}
   *
   * <p><b>Then:</b> Identity matched (by email). Updated existing inquiry: text.<br/>
   * {phoneNumber} is preserved, even it was not part of the update data.
   * {@code {id=id=019...3ca; email=foo@bar; phoneNumber=44111; text=inquiry1-rev4}}
   *
   * <p><b>When:</b> I provide inquiry 5 for submit,
   * with existing user {phoneNumber} from the inquiry 1.
   * {@code {id=null; phoneNumber=44111; text=inquiry1-rev5}}
   *
   * <p><b>Then:</b> Identity matched (by phoneNumber). Updated existing inquiry: text.<br/>
   * {email} is preserved, even it was not part of the update data.<br/>
   * {@code {id=id=019...3ca; email=foo@bar; phoneNumber=44111; text=inquiry1-rev5}}
   */
  @Test
  void testSubmitInquiry_acceptanceTestBasic() {
    var email = "test1001@example.org";

    var inquiry1 = new Inquiry();
    inquiry1.setEmail(email);
    inquiry1.setText("Test inquiry #1001.");

    var submittedInquiry1 = submitInquiryUseCase.submitInquiry(inquiry1);

    assertThat(submittedInquiry1.getId()).isNotNull();
    assertThat(submittedInquiry1.getEmail()).isEqualTo(email);
    assertThat(submittedInquiry1.getPhoneNumber()).isNull();
    assertThat(submittedInquiry1.getText()).isEqualTo("Test inquiry #1001.");
    assertThatOnlyOneInquiryExist(email);

    var phoneNumber = "1001 550 440";

    Inquiry inquiry2 = new Inquiry();
    inquiry2.setEmail(email);
    inquiry2.setPhoneNumber(phoneNumber);
    inquiry2.setText("Test inquiry #1001 - revision 2.");

    var submittedInquiry2 = submitInquiryUseCase.submitInquiry(inquiry2);

    UUID idGeneratedWithInquiry1 = submittedInquiry1.getId();

    assertThat(submittedInquiry2.getId()).isEqualTo(idGeneratedWithInquiry1);
    assertThat(submittedInquiry2.getEmail()).isEqualTo(email);
    assertThat(submittedInquiry2.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(submittedInquiry2.getText()).isEqualTo("Test inquiry #1001 - revision 2.");
    assertThatOnlyOneInquiryExist(email);

    Inquiry inquiry3 = new Inquiry();
    inquiry3.setEmail(email);
    inquiry3.setPhoneNumber(phoneNumber);
    inquiry3.setText("Test inquiry #1001 - revision 3.");

    var submittedInquiry3 = submitInquiryUseCase.submitInquiry(inquiry3);

    assertThat(submittedInquiry3.getId()).isEqualTo(idGeneratedWithInquiry1);
    assertThat(submittedInquiry3.getEmail()).isEqualTo(email);
    assertThat(submittedInquiry3.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(submittedInquiry3.getText()).isEqualTo("Test inquiry #1001 - revision 3.");
    assertThatOnlyOneInquiryExist(email);

    Inquiry inquiry4 = new Inquiry();
    inquiry4.setEmail(email);
    inquiry4.setText("Test inquiry #1001 - revision 4.");

    var submittedInquiry4 = submitInquiryUseCase.submitInquiry(inquiry4);

    assertThat(submittedInquiry4.getId()).isEqualTo(idGeneratedWithInquiry1);
    assertThat(submittedInquiry4.getEmail()).isEqualTo(email);
    assertThat(submittedInquiry4.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(submittedInquiry4.getText()).isEqualTo("Test inquiry #1001 - revision 4.");
    assertThatOnlyOneInquiryExist(email);

    Inquiry inquiry5 = new Inquiry();
    inquiry5.setPhoneNumber(phoneNumber);
    inquiry5.setText("Test inquiry #1001 - revision 5.");

    var submittedInquiry5 = submitInquiryUseCase.submitInquiry(inquiry5);
    assertThat(submittedInquiry5.getEmail()).isEqualTo(email);
    assertThat(submittedInquiry5.getPhoneNumber()).isEqualTo(phoneNumber);
    assertThat(submittedInquiry5.getText()).isEqualTo("Test inquiry #1001 - revision 5.");
    assertThatOnlyOneInquiryExist(email);

    assertThat(submittedInquiry5.getId()).isEqualTo(idGeneratedWithInquiry1);
  }

  @Test
  void testSubmitInquiry_validatePhoneNumber() {
    var inquiry = new Inquiry();
    inquiry.setPhoneNumber("*999");
    inquiry.setText("Test inquiry #1901.");

    assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> submitInquiryUseCase.submitInquiry(inquiry))
        .withMessageContaining("inquiry.phoneNumber")
        .withMessageContaining("Invalid character in the phone number");
  }

  @Test
  void testSubmitInquiry_validateEmail() {
    var inquiry1 = new Inquiry();
    inquiry1.setEmail("ab");
    inquiry1.setText("Test inquiry #1902.");

    assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> submitInquiryUseCase.submitInquiry(inquiry1))
        .withMessageContaining("inquiry.email")
        .withMessageContaining("Invalid email address format")
        .withMessageContaining("Text size out of the mininum/maximum length boundaries");

    var inquiry2 = new Inquiry();
    inquiry2.setEmail("fooEXAMPLE.org");
    inquiry2.setText("Test inquiry #1903.");

    assertThatExceptionOfType(ConstraintViolationException.class)
        .isThrownBy(() -> submitInquiryUseCase.submitInquiry(inquiry2))
        .withMessageContaining("inquiry.email")
        .withMessageContaining("Invalid email address format")
        .withMessageNotContaining("Text size out of the mininum/maximum length boundaries");
  }

  private void assertThatOnlyOneInquiryExist(String email) {
    var resultList = inquirySpringJdbcDao.findInquiryByEmail(email);
    assertThat(resultList).hasSize(1);
    assertThat(resultList.getFirst().getEmail()).isEqualTo(email);
  }
}