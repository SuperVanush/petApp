package com.example.demo.service;

import com.example.demo.exception.TransferException;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceTransfer {

    Transfer addTransfer(User lastUser, User toUser, String nameFromBill,
                         String NameToBill, BigDecimal transactionSumma);

    Transfer reduceBalance(String nameFromBill, BigDecimal reduceDigit);

    Transfer sumBalanceTransaction(String billName, BigDecimal sumDigit);

    TransferResponse transfer(TransferRequest request);

    TransferResponse reduceTransaction(TransferRequest request);

    TransferResponse sumTransaction(TransferRequest request);

    List<Transfer> findTransferByBillsName(String billName);

    TransferResponse transactionBetweenBill(TransferRequest request) throws TransferException;
}

