package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/to-user-reduce-transfer")
@RequiredArgsConstructor
public class ReduceFromUserTransferController implements Controller<TransferRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "User not found";

    private final UserService userService;
    private final BillService billService;
    private final TransferService transferService;

    private TransferRequest transferRequest(TransferRequest request) {
        return TransferRequest.builder()
                .idFromBill(request.getIdFromBill())
                .idToBill(request.getIdToBill())
                .sumTransfer(request.getSumTransfer())
                .build();
    }

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            String loginUser = transferRequest(request).getLoginFromUser();
            int idToBill = transferRequest(request).getIdFromBill();
            int idFromBill = transferRequest(request).getIdFromBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginUser);
            User reduceUser = userService.findUserByLogin(loginUser);
            transferService.reduceBalance(idFromBill, sumTransfer);
            transferService.addTransfer(fromUser, reduceUser, idFromBill, idToBill, sumTransfer);
            return getSuccessResponse(reduceUser.getUsername(),
                    billService.findBillById(idToBill).getBillName(),
                    billService.findBillById(idToBill).getBalance());
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage(), request.getLoginToUser());
        }
    }

    private TransferResponse getSuccessResponse(String fromUserName, String fromBillName, BigDecimal fromBillBalance) {
        return TransferResponse.builder()
                .message(SUCCESS_MESSAGE + ReduceFromUserTransferController.SUCCESS_MESSAGE)
                .fromUserName(fromUserName)
                .fromBillName(fromBillName)
                .fromBillBalance(fromBillBalance)
                .build();
    }

    private TransferResponse getErrorResponse(String message, String fromUserName) {
        return TransferResponse.builder()
                .message(ERROR_MESSAGE + message)
                .fromUserName(fromUserName)
                .build();
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
