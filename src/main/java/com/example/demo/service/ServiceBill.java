package com.example.demo.service;

import com.example.demo.dto.request.BillRequest;
import com.example.demo.dto.response.BillResponse;
import com.example.demo.dto.response.PrintBillResponse;
import com.example.demo.model.Bill;

import java.math.BigDecimal;
import java.util.UUID;

public interface ServiceBill {

    BillResponse addBill(BillRequest request);

    PrintBillResponse findBillsByUser(UUID userId);

    void deleteBill(UUID billId);

    Bill sumToBillTransfer(Bill toBill, BigDecimal sumTransfer);

    Bill reduceFromBillTransfer(Bill fromBill, BigDecimal sumTransfer);

    Bill findBillById(UUID id);
}
