package com.example.demo.service;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;

import java.util.List;

public interface ServiceTransfer {

    Transfer addTransfer(User lastUser, User toUser, int idFromBill,
                         int idToBill, int transactionSumma);

    Bill reduceBalance(int idBill, int reduceDigit);

    void transactionToBill(int idFromBill, int idToBill, int transactionSumma);

    Bill sumBalanceTransaction(int idBill, int sumDigit);

    int removeTransfer(int id);

    List<Transfer> findTransferByBillsId(int id);
}

