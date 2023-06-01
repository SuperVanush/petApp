package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.model.User;
import com.example.demo.model.dto.Request.BillRequest;
import com.example.demo.model.dto.Response.BillResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/bill")
@AllArgsConstructor
public class BillController implements Controller<BillRequest, BillResponse> {

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
            return new BillResponse("User " + "added bills  " + billService.findBillsByUser(findUser));
        } catch (MyExceptionBill bill) {
            return new BillResponse("Enter correct Login or Registration");
        }
    }

    @Override
    public Class<BillRequest> getRequestClass() {
        return BillRequest.class;
    }
}