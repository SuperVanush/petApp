package com.example.demo;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.response.BillDtoResponse;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class TestData {

    public static Bill createBillWithUser(User user) {
        return Bill.builder()
                .user(user)
                .billName("testBill" + randomInt())
                .balance(BigDecimal.valueOf(randomInt()))
                .build();
    }

    public static Bill createBill() {
        return Bill.builder()
                .id(randomInt())
                .billName("testBill" + randomInt())
                .balance(BigDecimal.valueOf(randomInt()))
                .build();
    }

    public static User createUser() {
        return User.builder()
                .login("login" + randomInt())
                .id(randomInt())
                .username("testUser" + randomInt())
                .password("password")
                .build();
    }

    public static int randomInt() {
        return (int) ((Math.random() * (10000 - 1)) + 1);
    }

    public static BillRequest createBillRequest(User user) {
        return BillRequest.builder()
                .login(user.getLogin())
                .billName("testBill" + randomInt())
                .balance(BigDecimal.valueOf(randomInt()))
                .build();
    }

    public static LoginRequest createLoginRequest(User user) {
        return LoginRequest.builder()
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }

    public static Transfer createTransfer(User fromUser, Bill fromBill, User toUser, Bill toBill, BigDecimal sumTransaction) {
        return Transfer.builder()
                .fromUser(fromUser)
                .fromBill(fromBill)
                .toUser(toUser)
                .toBill(toBill)
                .sumTransaction(BigDecimal.valueOf(randomInt()))
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static Transfer createTransferByBills(Bill fromBill, Bill toBill) {
        return Transfer.builder()
                .fromBill(fromBill)
                .toBill(toBill)
                .build();
    }

    public static BillDtoResponse createBillDtoResponse(User user, Bill bill) {
        return BillDtoResponse.builder()
                .userId(user.getId())
                .billName(bill.getBillName())
                .balance(bill.getBalance())
                .build();
    }
}