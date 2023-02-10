package com.example.demo.service;

import com.example.demo.model.Transfer;
import com.example.demo.model.User;

import java.util.List;

public interface ServiceTransfer {

    Transfer addTransfer(User lastUser, User toUser, int idFromBill,
                         int idToBill, int transactionSumma);

    int removeTransfer(int id);

    List<Transfer> findTransferByBillsId(int id);
}

