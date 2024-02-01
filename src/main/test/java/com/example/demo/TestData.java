package com.example.demo;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.request.LoginRequest;

import java.math.BigDecimal;

public class TestData {

    public static Bill createBillWithUser(User user) {
        return Bill.builder().user(user).billName("testBill" + randomInt()).balance(BigDecimal.valueOf(randomInt())).build();
    }

    public static Bill createBill() {
        return Bill.builder().billName("testBill" + randomInt()).balance(BigDecimal.valueOf(randomInt())).build();
    }

    public static User createUser() {
        return User.builder()
                .login("login"+ randomInt())
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

    public static LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .login("login")
                .password("password")
                .build();
    }
}