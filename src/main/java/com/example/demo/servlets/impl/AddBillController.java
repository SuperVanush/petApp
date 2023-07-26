package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillAddResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/add-bill")
@RequiredArgsConstructor
public class AddBillController implements Controller<BillRequest, BillAddResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String SUCCESS_BILL_MESSAGE = "Added bill with name";
    private static final String ERROR_USER_MESSAGE = "User is not found";
    private static final String ERROR_BILL_MESSAGE = "Bill is not added";

    private final UserService userService;
    private final BillService billService;

    @Override
    public BillAddResponse execute(BillRequest request) {
        try {
            String userLogin = request.getLogin();
            User findUser = userService.findUserByLogin(userLogin);

            String billName = request.getBillName();
            BigDecimal balance = request.getBalance();
            billService.addBill(billName, balance, findUser);

            return getSuccessResponse(SUCCESS_MESSAGE, request.getLogin(), SUCCESS_BILL_MESSAGE, request.getBillName());
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage(), request.getLogin(), e.getMessage(), request.getBillName());
        }
    }

    private BillAddResponse getSuccessResponse(String userMessage, String userLogin, String billMessage, String billName) {
        return BillAddResponse.builder()
                .userMessage(SUCCESS_MESSAGE + userMessage)
                .userLogin(userLogin)
                .billMessage(SUCCESS_BILL_MESSAGE + billMessage)
                .billName(billName)
                .build();
    }

    private BillAddResponse getErrorResponse(String message, String userLogin, String billMessage, String billName) {
        return BillAddResponse.builder()
                .userMessage(ERROR_USER_MESSAGE + message)
                .userLogin(userLogin)
                .billMessage(ERROR_BILL_MESSAGE + billMessage)
                .billName(billName)
                .build();
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}
