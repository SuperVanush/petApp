package com.example.demo.service;

import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeBill;

import java.util.UUID;

public interface ServiceTransfer {

    TransferResponse transferDistribution(TransferRequest request);

    PrintTransferResponse printTransfersByUser(UUID billId, TypeBill typeBill);
}