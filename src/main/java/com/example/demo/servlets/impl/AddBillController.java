package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDto;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/add-bill")
@RequiredArgsConstructor
public class AddBillController implements Controller<BillRequest, BillResponse> {

  private static final String SUCCESS_MESSAGE = "Success";
  private static final String ERROR_MESSAGE = "Enter correct Login or Registration, error message = ";

  private final UserService userService;
  private final BillService billService;
  private final Converter<Bill, BillDto> converter;

  @Override
  public BillResponse execute(BillRequest request) {
    try {
      String userLogin = request.getLogin();
      User findUser = userService.findUserByLogin(userLogin);

      String billName = request.getBillName();
      BigDecimal balance = request.getBalance();
      billService.addBill(billName, balance, findUser);

      List<Bill> userBills = billService.findBillsByUser(findUser);

      return getSuccessResponse(findUser.getLogin(), userBills);
    } catch (UserNotFoundException e) {
      return getErrorResponse(e.getMessage(), request.getLogin(), Collections.emptyList());
    }
  }

  private BillResponse getSuccessResponse(String login, List<Bill> userBills) {
    return BillResponse.builder()
        .message(SUCCESS_MESSAGE)
        .login(login)
        .billList(getResponseBillList(userBills))
        .build();
  }

  private BillResponse getErrorResponse(String message, String login, List<Bill> userBills) {
    return BillResponse.builder()
        .message(ERROR_MESSAGE + message)
        .login(login)
        .billList(getResponseBillList(userBills))
        .build();
  }

  private List<BillDto> getResponseBillList(List<Bill> responseBillList) {
    return responseBillList.stream().map(converter::convert).collect(Collectors.toList());
  }

  @Override
  public Class<BillRequest> getRequestClass() {
    return BillRequest.class;
  }
}
