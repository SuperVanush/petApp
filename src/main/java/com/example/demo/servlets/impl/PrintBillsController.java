package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.Request.PrintBillsRequest;
import com.example.demo.model.dto.Response.PrintBillsResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("/printbill")
@Data
@AllArgsConstructor
public class PrintBillsController implements Controller<PrintBillsRequest, PrintBillsResponse> {
    private final BillService billService;
    private final UserService userService;

    @Override
    public PrintBillsResponse execute(PrintBillsRequest request) {
        String login = request.getLogin();
        try {
            User findUser = userService.findUserByLogin(login);
            List<Bill> findUserBills = billService.findBillsByUser(findUser);
            return new PrintBillsResponse("Bills for User  " + findUser.getUsername() + "  " + findUserBills);
        } catch (MyExceptionBill e) {
            return new PrintBillsResponse("Bills not Found");
        }
    }


    @Override
    public Class<PrintBillsRequest> getRequestClass() {
        return PrintBillsRequest.class;
    }
}
