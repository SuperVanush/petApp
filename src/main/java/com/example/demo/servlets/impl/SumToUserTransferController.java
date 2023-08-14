package com.example.demo.servlets.impl;


import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
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

    private final UserService userService;
    private final TransferService transferService;

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            String loginToUser = request.getLoginToUser();
            int idFromBill = request.getIdToBill();
            int idToBill = request.getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginToUser);
            User toUser = userService.findUserByLogin(loginToUser);

            transferService.sumBalanceTransaction(idToBill, sumTransfer);
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);

            return getSuccessResponse(SUCCESS_MESSAGE);
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private TransferResponse getSuccessResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private TransferResponse getErrorResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
