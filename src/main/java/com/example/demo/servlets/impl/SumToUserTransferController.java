package com.example.demo.servlets.impl;


import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferBillRequest;
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
public class SumToUserTransferController implements Controller<TransferBillRequest, TransferResponse> {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "User not found";

    private final UserService userService;
    private final BillService billService;
    private final TransferService transferService;

    @Override
    public TransferResponse execute(TransferBillRequest request) {
        try {
            String loginToUser = request.getLoginUser();
            int idFromBill = request.getIdBill();
            int idToBill = request.getIdBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginToUser);
            User toUser = userService.findUserByLogin(loginToUser);

            transferService.sumBalanceTransaction(idToBill, sumTransfer);
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);

            return getSuccessResponse(toUser.getUsername(),
                    billService.findBillById(idToBill).getBillName(),
                    billService.findBillById(idToBill).getBalance());
        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage(), request.getLoginUser());
        }
    }

    private TransferResponse getSuccessResponse(String userName, String billName, BigDecimal newBillBalance) {
        return TransferResponse.builder().message(SUCCESS_MESSAGE + SumToUserTransferController.SUCCESS_MESSAGE).userName(userName).billName(billName).newBillBalance(newBillBalance).build();
    }

    private TransferResponse getErrorResponse(String message, String userName) {
        return TransferResponse.builder().message(ERROR_MESSAGE + message).userName(userName).build();
    }

    @Override
    public Class<TransferBillRequest> getRequestClass() {
        return TransferBillRequest.class;
    }
}
