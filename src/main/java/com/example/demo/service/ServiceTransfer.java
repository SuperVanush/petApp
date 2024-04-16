package com.example.demo.service;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeBill;

import java.math.BigDecimal;
import java.util.UUID;

public interface ServiceTransfer {

    TransferResponse transferDistribution(TransferRequest request);

    Transfer addTransfer(User fromUser, Bill fromBill, User toUser, Bill toBill, BigDecimal sumTransfer);

    TransferResponse depositOnBill(Bill toBill, BigDecimal sumTransfer);

    TransferResponse withdrawFromBill( Bill fromBill, BigDecimal sumTransfer);

    TransferResponse transferBetweenUsers(Bill fromBill, Bill toBill, BigDecimal sumTransfer);

    Bill sumToBillTransfer(Bill toBill, BigDecimal sumTransfer);

    Bill reduceFromBillTransfer(Bill fromBill, BigDecimal sumTransfer);

    PrintTransferResponse printTransfersByUser(UUID billId, TypeBill typeBill);
}
