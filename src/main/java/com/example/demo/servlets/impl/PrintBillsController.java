package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintBillsRequest;
import com.example.demo.model.dto.response.BillDto;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.stereotype.Service;

@Service("/print-bill")
@Builder
public class PrintBillsController implements Controller<PrintBillsRequest, BillResponse> {

  private static final String SUCCESS_MESSAGE = "Success";
  private static final String ERROR_MESSAGE = "Error";

  private final BillService billService;
  private final UserService userService;

  @Override
  public BillResponse execute(PrintBillsRequest request) {
    String login = request.getLogin();
    try {
      User findUser = userService.findUserByLogin(login);
      List<Bill> findUserBills = billService.findBillsByUser(findUser);
      String findUserName = findUser.getUsername();
      List<Bill> responseBillList = new ArrayList<>();
      for (Bill billInList : findUserBills) {
        Bill responseBill = Bill.builder().billName(billInList.getBillName()).balance(billInList.getBalance()).build();
        responseBillList.add(responseBill);
      }
      return getResponse(SUCCESS_MESSAGE, findUserName, responseBillList);
    } catch (MyExceptionBill | MyExceptionUser e) {
      return getResponse(e.getMessage(), ERROR_MESSAGE, Collections.emptyList());
    }
  }

  private BillResponse getResponse(String message, String userName, List<Bill> responseBillList) {
    return BillResponse.builder()
        .message(message)
        .userName(userName)
        .billList(getResponseBillList(responseBillList))
        .build();
  }

  private List<BillDto> getResponseBillList(List<Bill> responseBillList) {
    return responseBillList.stream().map(bill -> convertBill(bill)).collect(Collectors.toList());
  }

  private BillDto convertBill(Bill bill) {
    return BillDto.builder()
        .id(bill.getId())
        .billName(bill.getBillName())
        .balance(bill.getBalance())
        .userId(bill.getUser().getId())
        .build();
  }

  @Override
  public Class<PrintBillsRequest> getRequestClass() {
    return PrintBillsRequest.class;
  }
}
