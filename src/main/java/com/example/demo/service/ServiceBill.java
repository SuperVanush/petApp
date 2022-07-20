package com.example.demo.service;

import com.example.demo.model.User;

import java.util.List;

public interface ServiceBill<Bill> {

    void addBill(String billName, int billBalance, User user);

    List<Bill> findBillsByUser(User user);
}
