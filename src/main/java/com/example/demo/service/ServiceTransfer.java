package com.example.demo.service;

import com.example.demo.exception.TransferException;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;

import java.math.BigDecimal;

public interface ServiceTransfer {

    Transfer reduceBalance(String nameFromBill, BigDecimal reduceDigit);

    Transfer sumBalanceTransaction(String billName, BigDecimal sumDigit);

    TransferResponse transfer(TransferRequest request);

    TransferResponse reduceTransaction(TransferRequest request);

    TransferResponse sumTransaction(TransferRequest request);

    PrintTransferResponse findTransferByBillsName(TransferRequest request);

    TransferResponse transactionBetweenBill(TransferRequest request) throws TransferException;
}

