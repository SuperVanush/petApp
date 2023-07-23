package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferFromUserToUserRequest;
import com.example.demo.model.dto.response.TransferFromUserToUserResponse;
import com.example.demo.service.impl.TransferService;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("/from-user-to-user-transfer")
@RequiredArgsConstructor
public class TransferFromUserToUserController implements Controller<TransferFromUserToUserRequest, TransferFromUserToUserResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "User not found";

    private final UserService userService;
    private final TransferService transferService;

    @Override
    public TransferFromUserToUserResponse execute(TransferFromUserToUserRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            String loginToUser = request.getLoginToUser();
            int idFromBill = request.getIdFromBill();
            int idToBill = request.getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();

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

    private TransferFromUserToUserResponse getSuccessResponse(String fromUserName, String toUserName, String fromBillName, String toBillName, BigDecimal fromBillBalance, BigDecimal toBillBalance) {
        return TransferFromUserToUserResponse.builder()
                .message(SUCCESS_MESSAGE + TransferFromUserToUserController.SUCCESS_MESSAGE)
                .fromUserName(fromUserName)
                .toUserName(toUserName)
                .fromBillName(fromBillName)
                .toBillName(toBillName)
                .fromBillBalance(fromBillBalance)
                .toBillBalance(toBillBalance)
                .build();
    }

    private TransferFromUserToUserResponse getErrorResponse(String message, String fromUserName) {
        return TransferFromUserToUserResponse.builder()
                .message(ERROR_MESSAGE + message)
                .fromUserName(fromUserName).build();
    }


    @Override
    public Class<TransferFromUserToUserRequest> getRequestClass() {
        return TransferFromUserToUserRequest.class;
    }
}
