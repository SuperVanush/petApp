package com.example.demo.service;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceTransfer {

    Transfer addTransfer(User lastUser, User toUser, int idFromBill,
                         int idToBill, BigDecimal transactionSumma);

    Bill reduceBalance(int idBill, BigDecimal reduceDigit);

    void transactionToBill(int idFromBill, int idToBill, BigDecimal transactionSumma);

    Bill sumBalanceTransaction(int idBill, BigDecimal sumDigit);

    List<Transfer> findTransferByBillsId(int id);
}

