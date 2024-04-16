package com.example.demo.controller;

import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.PrintBillResponse;
import com.example.demo.service.impl.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @PostMapping("/add-bill")
    public BillResponse addBill(@RequestBody BillRequest request) {
        return billService.addBill(request);
    }

    @GetMapping("/bills-list")
    public PrintBillResponse findBillsByUser(@RequestParam BillRequest request) {
        return billService.findBillsByUser(request);
    }

    @PostMapping("/delete-bill")
    public BillResponse deleteBill(@RequestBody BillRequest request) {
        return billService.deleteBill(request);
    }
}
