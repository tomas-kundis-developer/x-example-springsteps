package org.springsteps.api.in.rest.v1;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springsteps.services.usecase.ListInquiryUseCase;
import org.springsteps.services.usecase.SubmitInquiryUseCase;

/**
 * Inquiry management REST service.
 */
@RestController
@RequestMapping(
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE,
    path = "{api-version}/inquiries", version = "v1")
@SuppressWarnings("MVCPathVariableInspection")
public class InquiryController {

  private final InquiryMapper mapper;

  private final ListInquiryUseCase listInquiryUseCase;

  private final SubmitInquiryUseCase submitInquiryUseCase;

  /**
   * Constructor.
   */
  public InquiryController(
      InquiryMapper mapper,
      ListInquiryUseCase listInquiryUseCase,
      SubmitInquiryUseCase submitInquiryUseCase) {
    this.listInquiryUseCase = listInquiryUseCase;
    this.mapper = mapper;
    this.submitInquiryUseCase = submitInquiryUseCase;
  }

  /**
   * List all inquiries.
   *
   * <p><em>Only for development purpose now.</em>
   */
  @GetMapping
  public List<InquiryApiModel> list() {
    List<InquiryApiModel> inquiryApiModels = new ArrayList<>();

    listInquiryUseCase.listAll()
        .forEach(inquiry -> inquiryApiModels.add(
            mapper.mapToInquiryApiModel(inquiry)
        ));

    return inquiryApiModels;
  }

  /**
   * Submit an inquiry.
   *
   * @return Submitted inquiry with newly generated id
   *     if there wasn't user identity match and new inquiry entry was created.
   */
  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  public InquiryApiModel submit(@Valid @RequestBody InquiryApiModel inquiryApiModel) {
    return mapper.mapToInquiryApiModel(
        submitInquiryUseCase.submitInquiry(mapper.mapToInquiry(inquiryApiModel)));
  }
}