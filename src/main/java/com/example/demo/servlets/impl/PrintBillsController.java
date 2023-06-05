package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintBillsRequest;
import com.example.demo.model.dto.response.PrintBillsResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service("/print-bill")
@RequiredArgsConstructor
public class PrintBillsController implements Controller<PrintBillsRequest, PrintBillsResponse> {

    private final BillService billService;
    private final UserService userService;

    private final String SUCCESS_MESSAGE = "Success";
    private final String ERROR_MESSAGE = "Error";

    @Override
    public PrintBillsResponse execute(PrintBillsRequest request) {
        String login = request.getLogin();
        try {
            User findUser = userService.findUserByLogin(login);
            List<Bill> findUserBills = billService.findBillsByUser(findUser);
            String findUserName = findUser.getUsername();
            return getResponse(findUserName, findUserBills, SUCCESS_MESSAGE);
        } catch (MyExceptionBill | MyExceptionUser e) {
            return getResponse(ERROR_MESSAGE, Collections.emptyList(), e.getMessage());
        }
    }

    private PrintBillsResponse getResponse(String name, List<Bill> billList, String message) {
        return PrintBillsResponse.builder()
                .name(name)
                .billList(billList)
                .message(message)
                .build();
    }

    @Override
    public Class<PrintBillsRequest> getRequestClass() {
        return PrintBillsRequest.class;
    }
}
