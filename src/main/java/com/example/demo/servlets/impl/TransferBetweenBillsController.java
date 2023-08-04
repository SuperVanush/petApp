package com.example.demo.servlets.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/transfer-between-bills")
@RequiredArgsConstructor
public class TransferBetweenBillsController implements Controller<TransferRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "ERROR";

    private final TransferService transferService;
    private final BillService billService;

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            int idFromBill = request.getIdFromBill();
            int idToBill = request.getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();

            transferService.transactionToBill(idFromBill, idToBill, sumTransfer);
            String fromBillName = billService.findBillById(idFromBill).getBillName();
            String toBillName = billService.findBillById(idToBill).getBillName();
            BigDecimal balanceFromBill = billService.findBillById(idFromBill).getBalance();
            BigDecimal balanceToBill = billService.findBillById(idToBill).getBalance();

            User fromUser = billService.findBillById(idFromBill).getUser();
            User toUser = billService.findBillById(idToBill).getUser();
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);

            return getSuccessResponse(fromBillName, balanceFromBill, toBillName, balanceToBill);
        } catch (TransferException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private TransferResponse getSuccessResponse(String fromBillName, BigDecimal fromBillBalance, String toBillName, BigDecimal toBillBalance) {
        return TransferResponse.builder()
                .message(TransferBetweenBillsController.SUCCESS_MESSAGE)
                .fromBillName(fromBillName)
                .fromBillBalance(fromBillBalance)
                .toBillName(toBillName)
                .toBillBalance(toBillBalance)
                .build();
    }

    private TransferResponse getErrorResponse(String message) {
        return TransferResponse.builder()
                .message(ERROR_MESSAGE)
                .build();
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
