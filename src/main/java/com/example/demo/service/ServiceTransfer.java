package com.example.demo.service;

import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;

import java.math.BigDecimal;

public interface ServiceTransfer {

    Transfer reduceBalance(int idBill, BigDecimal reduceDigit);

    Transfer sumBalanceTransaction(int idBill, BigDecimal sumDigit);

    PrintTransferResponse findTransferByBillsName(PrintTransferRequest request);
}

