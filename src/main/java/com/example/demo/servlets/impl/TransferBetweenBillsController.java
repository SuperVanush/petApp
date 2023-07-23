package com.example.demo.servlets.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.model.dto.request.TransferBetweenBillsRequest;
import com.example.demo.model.dto.response.TransferBetweenBillResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/transfer-between-bills")
@RequiredArgsConstructor
public class TransferBetweenBillsController implements Controller<TransferBetweenBillsRequest, TransferBetweenBillResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "ERROR";

    private final TransferService transferService;
    private final BillService billService;

    @Override
    public TransferBetweenBillResponse execute(TransferBetweenBillsRequest request) {
        try {
            int idFromBill = request.getIdFromBill();
            int idToBill = request.getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            transferService.transactionToBill(idFromBill, idToBill, sumTransfer);
            String fromBillName = billService.findBillById(idFromBill).getBillName();
            String toBillName = billService.findBillById(idToBill).getBillName();
            BigDecimal balanceFromBill = billService.findBillById(idFromBill).getBalance();
            BigDecimal balanceToBill = billService.findBillById(idToBill).getBalance();

            return new TransferBetweenBillResponse(SUCCESS_MESSAGE, fromBillName, balanceFromBill, toBillName, balanceToBill);
        } catch (TransferException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private TransferBetweenBillResponse getErrorResponse(String message) {
        return TransferBetweenBillResponse.builder()
                .message(ERROR_MESSAGE + message)
                .build();
    }

    @Override
    public Class<TransferBetweenBillsRequest> getRequestClass() {
        return TransferBetweenBillsRequest.class;
    }
}
