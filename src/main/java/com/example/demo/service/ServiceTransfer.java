package com.example.demo.service;

import com.example.demo.dto.request.TransferRequest;
import com.example.demo.dto.response.PrintTransferResponse;
import com.example.demo.dto.response.TransferResponse;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.types.TypeBill;

import java.math.BigDecimal;
import java.util.UUID;

public interface ServiceTransfer {

    Transfer addTransfer(Bill fromBill, Bill toBill, BigDecimal sumTransfer);

    TransferResponse transferDistribution(TransferRequest request);

    PrintTransferResponse printTransfersByUser(UUID billId, TypeBill typeBill);
}