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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("/add-bill")
@RequiredArgsConstructor
public class AddBillController implements Controller<BillRequest, BillResponse> {

    private final static String SUCCESS_MESSAGE = "Success";
    private final static String ERROR_MESSAGE = "Enter correct Login or Registration";

    private final UserService userService;
    private final BillService billService;

    @Override
    public BillResponse execute(BillRequest request) {
        String userLogin = request.getLogin();
        String billName = request.getBillName();
        BigDecimal balance = request.getBalance();
        try {
            User findUser = userService.findUserByLogin(userLogin);
            billService.addBill(billName, balance, findUser);
            List<Bill> billsFindUser = billService.findBillsByUser(findUser);
            List<Bill> responseBillList = new ArrayList<>();
            for (Bill billInList : billsFindUser) {
                Bill responseBill = Bill.builder()
                        .billName(billInList.getBillName())
                        .balance(billInList.getBalance())
                        .build();
                responseBillList.add(responseBill);
            }
            return getBillResponse(SUCCESS_MESSAGE, findUser.getUsername(), responseBillList);
        } catch (MyExceptionBill e) {
            return getBillResponse(e.getMessage(), ERROR_MESSAGE, Collections.emptyList());
        }
    }

    private BillResponse getBillResponse(String message, String userName, List<Bill> responseBillList) {
        return BillResponse.builder()
                .message(message)
                .userName(userName)
                .billList(responseBillList)
                .build();
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}