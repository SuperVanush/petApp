package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.TransferService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/from-user-to-user-transfer")
@RequiredArgsConstructor
public class TransferFromUserToUserController implements Controller<TransferRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "User not found";

    private final UserService userService;
    private final TransferService transferService;

    private TransferRequest transferRequest(TransferRequest request) {
        return TransferRequest.builder()
                .loginFromUser(request.getLoginFromUser())
                .loginToUser(request.getLoginToUser())
                .idFromBill(request.getIdFromBill())
                .idToBill(request.getIdToBill())
                .sumTransfer(request.getSumTransfer())
                .build();
    }

    @Override
    public TransferResponse execute(TransferRequest request) {
        try {
            String loginFromUser = transferRequest(request).getLoginFromUser();
            String loginToUser = transferRequest(request).getLoginToUser();
            int idFromBill = transferRequest(request).getIdFromBill();
            int idToBill = transferRequest(request).getIdToBill();
            BigDecimal sumTransfer = transferRequest(request).getSumTransfer();

            User fromUser = userService.findUserByLogin(loginFromUser);
            User toUser = userService.findUserByLogin(loginToUser);
            Bill toBill = transferService.sumBalanceTransaction(idToBill, sumTransfer);
            Bill fromBill = transferService.reduceBalance(idFromBill, sumTransfer);
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);

            return getSuccessResponse(fromUser.getUsername(), toUser.getUsername(), fromBill.getBillName(), toBill.getBillName(), fromBill.getBalance(), toBill.getBalance());
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage(), request.getLoginFromUser());
        }
    }

    private TransferResponse getSuccessResponse(String fromUserName, String toUserName, String fromBillName, String toBillName, BigDecimal fromBillBalance, BigDecimal toBillBalance) {
        return TransferResponse.builder()
                .message(SUCCESS_MESSAGE + TransferFromUserToUserController.SUCCESS_MESSAGE)
                .fromUserName(fromUserName)
                .toUserName(toUserName)
                .fromBillName(fromBillName)
                .toBillName(toBillName)
                .fromBillBalance(fromBillBalance)
                .toBillBalance(toBillBalance)
                .build();
    }

    private TransferResponse getErrorResponse(String message, String fromUserName) {
        return TransferResponse.builder()
                .message(ERROR_MESSAGE + message)
                .fromUserName(fromUserName).build();
    }


    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}
