package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Bill;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceBill {

    void addBill(String billName, BigDecimal billBalance, User user);

    List<Bill> findBillsByUser(User user);

    Bill findBillById(int id);

}