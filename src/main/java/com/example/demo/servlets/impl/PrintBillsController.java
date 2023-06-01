package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.exception.MyExceptionUser;
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

@Service("/print-bill")
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
            String findUserName = findUser.getUsername();
            return new PrintBillsResponse("Bills for User  " + findUserName + "  " + findUserBills);
        } catch (MyExceptionBill | MyExceptionUser e) {

            return new PrintBillsResponse(e.getMessage());
        }
    }


    @Override
    public Class<PrintBillsRequest> getRequestClass() {
        return PrintBillsRequest.class;
    }
}
