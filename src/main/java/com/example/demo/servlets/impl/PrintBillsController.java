package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintBillsRequest;
import com.example.demo.model.dto.response.BillDto;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/print-bill")
@RequiredArgsConstructor
public class PrintBillsController implements Controller<PrintBillsRequest, BillResponse> {

  private static final String SUCCESS_MESSAGE = "Success";
  private static final String ERROR_MESSAGE = "Error = ";

  private final BillService billService;
  private final UserService userService;
  private final Converter<Bill, BillDto> converter;

  @Override
  public BillResponse execute(PrintBillsRequest request) {
    try {
      User userByLogin = userService.findUserByLogin(request.getLogin());

      return getSuccessResponse(userByLogin);
    } catch (UserNotFoundException e) {
      return getErrorResponse(e.getMessage(), request.getLogin());
    }
  }

  private BillResponse getSuccessResponse(User user) {
    return BillResponse.builder()
        .message(SUCCESS_MESSAGE)
        .login(user.getLogin())
        .billList(getResponseBills(user))
        .build();
  }

  private List<BillDto> getResponseBills(User user) {
    return billService.findBillsByUser(user).stream().map(converter::convert).collect(Collectors.toList());
  }

  private BillResponse getErrorResponse(String message, String login) {
    return BillResponse.builder()
        .message(ERROR_MESSAGE + message)
        .login(login)
        .build();
  }

  @Override
  public Class<PrintBillsRequest> getRequestClass() {
    return PrintBillsRequest.class;
  }
}
