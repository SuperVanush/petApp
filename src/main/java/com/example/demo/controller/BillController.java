package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;
    private final UserService userService;
    private final Converter<Bill, BillDtoResponse> converter;

    @PostMapping("/add-bill")
    public BillResponse addBill(@RequestBody BillRequest request) {
        try {
            String userLogin = request.getLogin();
            String billName = request.getBillName();
            BigDecimal balance = request.getBalance();
            User userOfBill = userService.findUserByLogin(userLogin);
            billService.addBill(billName, balance, userOfBill);
            return getSuccessAddBillResponse(userOfBill);
        } catch (UserNotFoundException e) {
            return getErrorAddBillResponse(e.getMessage());
        }
    }

    @GetMapping("/bills-by-user")
    public BillResponse findBillsByUser(@RequestBody BillRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());
            return getSuccessFindBillResponse(userByLogin);
        } catch (UserNotFoundException e) {
            return getErrorFindBillResponse(e.getMessage());
        }
    }


    private BillResponse getSuccessAddBillResponse(User user) {
        return BillResponse.builder()
                .message("Success")
                .login(user.getLogin())
                .billList(getResponseBills(user))
                .build();
    }

    private BillResponse getErrorAddBillResponse(String message) {
        return BillResponse.builder()
                .message(message)
                .build();
    }

    private BillResponse getSuccessFindBillResponse(User user) {
        return BillResponse.builder()
                .message("Success")
                .login(user.getLogin())
                .billList(getResponseBills(user))
                .build();
    }

    private List<BillDtoResponse> getResponseBills(User user) {
        return billService.findBillsByUser(user).stream().
                map(converter::convert).collect(Collectors.toList());
    }

    private BillResponse getErrorFindBillResponse(String message) {
        return BillResponse.builder()
                .message(message)
                .build();
    }
}