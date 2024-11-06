package com.example.demo.controller;

import com.example.demo.dto.request.BillRequest;
import com.example.demo.dto.response.BillResponse;
import com.example.demo.dto.response.PrintBillResponse;
import com.example.demo.service.ServiceBill;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final ServiceBill serviceBill;

    @PostMapping("/add-bill")
    public BillResponse addBill(@RequestBody BillRequest request) {
        return serviceBill.addBill(request);
    }

    @GetMapping("/bills-list/{userId}")
    public PrintBillResponse findBillsByUser(@PathVariable UUID userId) {
        return serviceBill.findBillsByUser(userId);
    }

    @PostMapping("/delete-bill")
    public void deleteBill(@RequestBody UUID billId) {
        serviceBill.deleteBill(billId);
    }
}
