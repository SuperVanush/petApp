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

            return getSuccessResponse(SUCCESS_MESSAGE);
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private BillAddResponse getSuccessResponse(String message) {
        return BillAddResponse.builder()
                .userMessage(message)
                .build();
    }

    private BillAddResponse getErrorResponse(String message) {
        return BillAddResponse.builder()
                .userMessage(message)
                .build();
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}
