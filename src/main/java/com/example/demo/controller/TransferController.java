package com.example.demo.controller;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.service.impl.TransferService;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransferController {

    private final UserService userService;
    private final TransferService transferService;

    @PostMapping("/sumSratsfer")
    public TransferResponse addTransfer(@RequestBody TransferRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            String nameFromBill = request.getNameFromBill();
            String loginToUser = request.getLoginToUser();
            String nameToBill = request.getNameToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            Transfer transfer = transferService.transactionBetweenBill(loginFromUser, loginToUser, nameFromBill, nameToBill, sumTransfer);
            return getSuccessAddTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/sum-transaction")
    public TransferResponse addSum(@RequestBody TransferRequest request) {
        try {
            String nameToBill = request.getNameToBill();
            BigDecimal sumDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            String nameToBillForTransfer = transferService.sumBalanceTransaction(nameToBill, sumDigit).getBillName();
            Transfer transfer = transferService.addTransfer(user, user, nameToBillForTransfer, nameToBillForTransfer, sumDigit);
            return getSuccessTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/reduce-transaction")
    public TransferResponse addReduce(@RequestBody TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            String nameFromBill = transferService.reduceBalance(billName, reduceDigit).getBillName();

            Transfer transfer = transferService.addTransfer(user, user, nameFromBill, nameFromBill, reduceDigit);
            return getSuccessTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @GetMapping("/transactions-by-bill")
    public PrintTransferResponse printTransferByBill(@RequestBody TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            List<Transfer> transferList = transferService.findTransferByBillsName(billName);
            return getSuccessPrintTransferResponse(transferList);

        } catch (UserNotFoundException e) {
            return getErrorPrintTransferResponse(e.getMessage());
        }
    }

    private TransferResponse getSuccessAddTransferResponse(int idTransaction) {
        return TransferResponse.builder()
                .message("Success")
                .idTransaction(idTransaction)
                .build();
    }

    private TransferResponse getErrorAddTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private TransferResponse getSuccessTransferResponse(int idTransaction) {
        return TransferResponse.builder()
                .message("Success")
                .idTransaction(idTransaction)
                .build();
    }

    private TransferResponse getErrorTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private PrintTransferResponse getSuccessPrintTransferResponse(List<Transfer> transferList) {
        return PrintTransferResponse.builder()
                .message("Success")
                .transferList(transferList)
                .build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }
}