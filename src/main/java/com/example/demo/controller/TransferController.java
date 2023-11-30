package com.example.demo.controller;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
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

    @PostMapping("/transfer-from-user-to-user")
    public TransferResponse addTransfer(@RequestBody TransferRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            int idFromBill = request.getIdFromBill();
            String loginToUser = request.getLoginToUser();
            String nameToBill = request.getNameToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginFromUser);
            User toUser = userService.findUserByLogin(loginToUser);
            transferService.transactionToBill(idFromBill, idToBill, sumTransfer);
            Transfer transfer = transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);
            return getSuccessAddTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/sum-transaction-cash-to-user")
    public TransferResponse addSum(@RequestBody TransferRequest request) {
        try {
            String nameToBill = request.getNameToBill();
            BigDecimal sumDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            Bill bill = transferService.sumBalanceTransaction(idBill, sumDigit);
            int idAddedBill = bill.getId();
            Transfer transfer = transferService.addTransfer(user, user, idAddedBill, idAddedBill, sumDigit);
            return getSuccessTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/reduce-transaction")
    public TransferResponse addReduce(@RequestBody TransferRequest request) {
        try {
            int idBill = request.getIdToBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            Bill bill = transferService.reduceBalance(idBill, reduceDigit);
            int idAddedBill = bill.getId();
            Transfer transfer = transferService.addTransfer(user, user, idAddedBill, idAddedBill, reduceDigit);
            return getSuccessTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @GetMapping("/transactions-by-bill")
    public PrintTransferResponse printTransferByBill(@RequestBody TransferRequest request) {
        try {
            int idBill = request.getIdFromBill();
            List<Transfer> transferList = transferService.findTransferByBillsId(idBill);
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