package com.example.demo.controller;

import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.model.types.TypeBill;
import com.example.demo.service.ServiceTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final ServiceTransfer serviceTransfer;

    @PostMapping("/transfer")
    public TransferResponse transferDistribution(@RequestBody TransferRequest request) {
        return serviceTransfer.transferDistribution(request);
    }

    @GetMapping("/transfers-list/{id}/{typeBill}")
    public PrintTransferResponse printTransfers(@PathVariable("id") UUID id, @PathVariable("typeBill") TypeBill typeBill) {
        return serviceTransfer.printTransfersByUser(id, typeBill);
    }
}
