package com.example.demo.service;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceTransfer {

    Transfer addTransfer(User lastUser, User toUser, String nameFromBill,
                         String NameToBill, BigDecimal transactionSumma);

    Bill reduceBalance(String nameFromBill, BigDecimal reduceDigit);

    Transfer transactionBetweenBill(String fromUserLogin, String toUserLogin, String nameFromBill, String nameToBill, BigDecimal transactionSumma);

    Bill sumBalanceTransaction(String billName, BigDecimal sumDigit);

    List<Transfer> findTransferByBillsName(String billName);
}

