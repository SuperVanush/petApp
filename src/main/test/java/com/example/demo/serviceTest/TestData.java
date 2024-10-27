package com.example.demo.serviceTest;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestData {
    public static int createIntRandom() {
        return (int) ((Math.random() * (1000 - 1)) + 1);
    }

    public static UUID createId() {
        return UUID.randomUUID();
    }

    public static User createUser() {
        return User.builder()
                .userName("userName" + createIntRandom())
                .login("login" + createIntRandom())
                .id(createId())
                .build();
    }

    public static User createUserWithoutId() {
        return User.builder()
                .userName("userName" + createIntRandom())
                .login("login" + createIntRandom())
                .password("password" + createIntRandom())
                .build();
    }

    public static Bill createBill(User user) {
        return Bill.builder()
                .billName("billName" + createIntRandom())
                .id(createId())
                .user(user)
                .balance(BigDecimal.valueOf(createIntRandom()))
                .build();
    }

    public static Bill createBillWithoutId(User user) {
        return Bill.builder()
                .billName("billName" + createIntRandom())
                .user(user)
                .balance(BigDecimal.valueOf(createIntRandom()))
                .build();
    }

    public static Bill createBillWithoutUser() {
        return Bill.builder()
                .id(createId())
                .billName("billName" + createIntRandom())
                .balance(BigDecimal.valueOf(createIntRandom()))
                .build();
    }

    public static Transfer createTransferBetweenUsers(Bill fromBill, User fromUser, Bill toBill, User toUser, BigDecimal sumTransfer) {
        return Transfer.builder()
                .id(createId())
                .fromUser(fromUser)
                .toUser(toUser)
                .fromBill(fromBill)
                .toBill(toBill)
                .sumTransfer(sumTransfer)
                .localDateTime(LocalDateTime.now())
                .build();
    }

    public static Transfer createTransferBetweenBills(Bill bill, User user, BigDecimal sumTransfer) {
        return Transfer.builder()
                .id(createId())
                .toUser(user)
                .fromBill(bill)
                .fromUser(user)
                .toBill(bill)
                .sumTransfer(sumTransfer)
                .localDateTime(LocalDateTime.now())
                .build();
    }
}
