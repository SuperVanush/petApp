package com.example.demo.service.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.ServiceBill;
import com.example.demo.service.converter.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService implements ServiceBill {

    private final BillRepository billRepository;
    private final UserService userService;
    private final Converter<Bill, BillDtoResponse> converter;

    @Override
    public BillResponse addBill(BillRequest request) {
        try {
            String userLogin = request.getLogin();
            String billName = request.getBillName();
            BigDecimal billBalance = request.getBalance();
            User userOfBill = userService.findUserByLogin(userLogin);
            Bill bill = Bill.builder().billName(billName).balance(billBalance).user(userOfBill).build();
            billRepository.save(bill);
            return getSuccessAddBillResponse(userOfBill);
        } catch (UserNotFoundException e) {
            return getErrorAddBillResponse(e.getMessage());
        }
    }

    @Override
    public BillResponse findBillsByUser(BillRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());
            return getSuccessFindBillResponse(userByLogin);
        } catch (UserNotFoundException e) {
            return getErrorFindBillResponse(e.getMessage());
        }
    }

    @Override
    public Bill findBillByName(String billName) {
        return billRepository.findBillByBillName(billName).orElseThrow(() -> new UserNotFoundException("Bill not found"));
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
        return billRepository.findAll().stream().filter(bill -> user.equals(bill.getUser())).map(converter::convert).collect(Collectors.toList());
    }

    private BillResponse getErrorFindBillResponse(String message) {
        return BillResponse.builder()
                .message(message)
                .build();
    }
}
