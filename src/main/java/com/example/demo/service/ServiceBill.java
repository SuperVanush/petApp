package com.example.demo.service;

import com.example.demo.model.Bill;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;

public interface ServiceBill {

    BillResponse findBillsByUser(BillRequest request);

    Bill findBillByName(String billName);
}