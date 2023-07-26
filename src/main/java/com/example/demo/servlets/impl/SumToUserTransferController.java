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

@Service("/to-user-sum-transfer")
@RequiredArgsConstructor
public class SumToUserTransferController implements Controller<TransferRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "User not found";

    private final UserService userService;
    private final BillService billService;
    private final TransferService transferService;

    private TransferRequest transferRequest(TransferRequest request) {
        return TransferRequest.builder()
                .loginToUser(request.getLoginToUser())
                .idToBill(request.getIdToBill())
                .sumTransfer(request.getSumTransfer())
                .build();
    }

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            String loginToUser = transferRequest(request).getLoginToUser();
            int idFromBill = transferRequest(request).getIdToBill();
            int idToBill = transferRequest(request).getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginToUser);
            User toUser = userService.findUserByLogin(loginToUser);

            transferService.sumBalanceTransaction(idToBill, sumTransfer);
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);

            return getSuccessResponse(toUser.getUsername(),
                    billService.findBillById(idToBill).getBillName(),
                    billService.findBillById(idToBill).getBalance());
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage(), request.getLoginToUser());
        }
    }

    private TransferResponse getSuccessResponse(String toUserName, String toBillName, BigDecimal toBillBalance) {
        return TransferResponse.builder()
                .message(SUCCESS_MESSAGE + SumToUserTransferController.SUCCESS_MESSAGE)
                .toUserName(toUserName)
                .toBillName(toBillName)
                .toBillBalance(toBillBalance)
                .build();
    }

    private TransferResponse getErrorResponse(String message, String toUserName) {
        return TransferResponse.builder()
                .message(ERROR_MESSAGE + message)
                .toUserName(toUserName).build();
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
