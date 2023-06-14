package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service("/bill")
@RequiredArgsConstructor
public class BillController implements Controller<BillRequest, BillResponse> {

    private final static String SUCCESS_MESSAGE = "Success";
    private final static String ERROR_MESSAGE = "Enter correct Login or Registration";

    private final UserService userService;
    private final BillService billService;

    @Override
    public BillResponse execute(BillRequest request) {
        String userLogin = request.getLogin();
        String billName = request.getBillName();
        BigDecimal billBalance = request.getBalance();
        try {
            User findUser = userService.findUserByLogin(userLogin);
            billService.addBill(billName, billBalance, findUser);
            List<Bill> billsFindUser = billService.findBillsByUser(findUser);
            return getBillResponse(findUser.getUsername(), SUCCESS_MESSAGE, billsFindUser);
        } catch (MyExceptionBill e) {
            return getBillResponse(ERROR_MESSAGE, e.getMessage(), Collections.emptyList());
        }
    }

    private BillResponse getBillResponse(String userName, String message, List<Bill> billList) {
        return BillResponse.builder()
                .userName(userName)
                .message(message)
                .billList(billList)
                .build();
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}