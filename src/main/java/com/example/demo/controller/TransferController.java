package com.example.demo.controller;

import com.example.demo.model.dto.request.PrintTransferRequest;
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

    @PostMapping("/transfer")
    public TransferResponse transferDistribution(@RequestBody TransferRequest request) {
        return transferService.transferDistribution(request);
    }

    @GetMapping("/print-transfers")
    public PrintTransferResponse printTransfers(@RequestBody PrintTransferRequest request) {
        return transferService.printTransfersByUser(request);
    }
}

