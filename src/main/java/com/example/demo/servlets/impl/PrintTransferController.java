package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.service.impl.TransferService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("/print-transfer")
@RequiredArgsConstructor
public class PrintTransferController implements Controller<TransferRequest, PrintTransferResponse> {

    private final TransferService transferService;

    @Override
    public PrintTransferResponse execute(TransferRequest request) {
        try {
            int idFromBill = request.getIdFromBill();
            List<Transfer> transferList = transferService.findTransferByBillsId(idFromBill);
            return getSuccessResponse(transferList);

        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private PrintTransferResponse getSuccessResponse(List<Transfer> transferList) {
        return PrintTransferResponse.builder()
                .transferList(transferList)
                .build();
    }

    private PrintTransferResponse getErrorResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
