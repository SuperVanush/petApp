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

@Service("/to-user-reduce-transfer")
@RequiredArgsConstructor
public class ReduceFromUserTransferController implements Controller<TransferRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";

    private final UserService userService;
    private final TransferService transferService;

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            String loginUser = request.getLoginFromUser();
            int idToBill = request.getIdFromBill();
            int idFromBill = request.getIdFromBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginUser);
            User reduceUser = userService.findUserByLogin(loginUser);
            transferService.reduceBalance(idFromBill, sumTransfer);
            transferService.addTransfer(fromUser, reduceUser, idFromBill, idToBill, sumTransfer);
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
