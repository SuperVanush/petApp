package com.example.demo.service;

import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.PrintBillResponse;

public interface ServiceBill {

    BillResponse addBill(BillRequest request);

    PrintBillResponse findBillsByUser(BillRequest request);
}
