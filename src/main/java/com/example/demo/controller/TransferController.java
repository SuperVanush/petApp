package com.example.demo.controller;

import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transaction")
    public TransferResponse transaction(@RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }

    @GetMapping("/transactions-by-bill")
    public PrintTransferResponse printTransferByBill(@RequestBody TransferRequest request) {
        return transferService.findTransferByBillsName(request);
    }
}