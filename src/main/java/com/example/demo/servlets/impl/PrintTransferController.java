package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Transfer;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.service.impl.TransferService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("/print-transfer")
@RequiredArgsConstructor
public class PrintTransferController implements Controller<PrintTransferRequest, PrintTransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "Not found User";

    private final TransferService transferService;

    @Override
    public PrintTransferResponse execute(PrintTransferRequest request) {
        try {
            int idBill = request.getIdBill();
            List<Transfer> transferList = transferService.findTransferByBillsId(idBill);
            return new PrintTransferResponse(SUCCESS_MESSAGE, transferList);
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private PrintTransferResponse getErrorResponse(String message) {
        return PrintTransferResponse.builder()
                .message(ERROR_MESSAGE + message)
                .build();
    }

    @Override
    public Class<PrintTransferRequest> getRequestClass() {
        return PrintTransferRequest.class;
    }
}
