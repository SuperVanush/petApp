package com.example.demo.controller;

import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeBill;
import com.example.demo.service.impl.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public TransferResponse transferDistribution(@RequestBody TransferRequest request) {
        return transferService.transferDistribution(request);
    }

    @GetMapping("/transfers-list/{billId}/{typeBill}")
    public PrintTransferResponse printTransfers(@PathVariable UUID billId, @PathVariable TypeBill typeBill) {
        return transferService.printTransfersByUser(billId, typeBill);
    }
}

