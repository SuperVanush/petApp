package com.example.demo.service;

import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.PrintBillResponse;

import java.util.UUID;

public interface ServiceBill {

    BillResponse addBill(BillRequest request);

    PrintBillResponse findBillsByUser(UUID userId);
}
