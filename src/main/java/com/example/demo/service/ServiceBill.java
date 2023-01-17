package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.Bill;

import java.util.List;

public interface ServiceBill {

    void addBill(String billName, int billBalance, User user);

    List<Bill> findBillsByUser(User user);

    Bill sumBalanceTransaction(int idBill, int sumDigit);

    Bill reduceBalance(int idBill, int reduceDigit);

    void transactionBetweenBills(int idFromBill, int idToBill, int transactionSumma);

    void transactionBetweenUsers(int idFromBill, int idToBill, int transactionSumma);

    void transactionToRandomBill(int idFromBill, User toUser, int transactionSumma);

    void realizeTransaction(Bill fromBill, Bill toBill, int transactionSumma);
}