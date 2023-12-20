package com.example.demo.service;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;

import java.math.BigDecimal;

public interface ServiceTransfer {

    Transfer reduceBalance(String nameFromBill, BigDecimal reduceDigit);

    Transfer sumBalanceTransaction(String billName, BigDecimal sumDigit);

    PrintTransferResponse findTransferByBillsName(TransferRequest request);
}

