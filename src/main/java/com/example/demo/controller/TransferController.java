package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transaction")
    public TransferResponse transaction(@RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }

    @PostMapping("/sum-transfer")
    public TransferResponse addTransfer(@RequestBody TransferRequest request) {
        return transferService.transactionBetweenBill(request);
    }

    @GetMapping("/transactions-by-bill")
    public PrintTransferResponse printTransferByBill(@RequestBody TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            List<Transfer> transferList = transferService.findTransferByBillsName(billName);
            return getSuccessPrintTransferResponse(transferList);

        } catch (UserNotFoundException e) {
            return getErrorPrintTransferResponse(e.getMessage());
        }
    }

    private PrintTransferResponse getSuccessPrintTransferResponse(List<Transfer> transferList) {
        return PrintTransferResponse.builder()
                .message("Success")
                .transferList(transferList)
                .build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }
}