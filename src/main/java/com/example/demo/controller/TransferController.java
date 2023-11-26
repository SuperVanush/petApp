package com.example.demo.controller;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.impl.BillService;
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

    private final TransferRepository transferRepository;

    private final BillService billService;
    private final UserService userService;
    private final TransferService transferService;

    @PostMapping("/transfer-from-user-to-user")
    public TransferResponse addTransfer(@RequestBody TransferRequest request) {
        try {
            transferRepository.findAll();
            String loginFromUser = request.getLoginFromUser();
            int idFromBill = request.getIdFromBill();
            String loginToUser = request.getLoginToUser();
            int idToBill = request.getIdToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            User fromUser = userService.findUserByLogin(loginFromUser);
            User toUser = userService.findUserByLogin(loginToUser);
            String nameFromUser = fromUser.getUsername();
            String nameToUser = toUser.getUsername();
            String nameFromBill = billService.findBillById(idFromBill).getBillName();
            String nameToBill = billService.findBillById(idToBill).getBillName();
            transferService.sumBalanceTransaction(idToBill, sumTransfer);
            transferService.reduceBalance(idFromBill, sumTransfer);
            transferService.addTransfer(fromUser, toUser, idFromBill, idToBill, sumTransfer);
            BigDecimal balanceFromBill = billService.findBillById(idFromBill).getBalance();
            BigDecimal balanceToBill = billService.findBillById(idToBill).getBalance();
            return getSuccessAddTransferResponse(nameFromUser, nameToUser, nameFromBill, nameToBill, balanceFromBill, balanceToBill);
        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/sum-transaction-cash-to-user")
    public TransferResponse addSum(@RequestBody TransferRequest request) {
        try {
            transferRepository.findAll();
            int idBill = request.getIdToBill();
            BigDecimal sumDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            Bill bill = transferService.sumBalanceTransaction(idBill, sumDigit);
            transferService.addTransfer(user, user, idBill, idBill, sumDigit);
            return getSuccessTransferResponse(bill);
        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @PostMapping("/reduce-transaction")
    public TransferResponse addReduce(@RequestBody TransferRequest request) {
        try {
            transferRepository.findAll();
            int idBill = request.getIdToBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            Bill bill = transferService.reduceBalance(idBill, reduceDigit);
            transferService.addTransfer(user, user, idBill, idBill, reduceDigit);
            return getSuccessTransferResponse(bill);
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

    private TransferResponse getSuccessAddTransferResponse(String nameFromUser, String nameToUser, String nameFromBill, String nameToBill, BigDecimal balanceFromBill, BigDecimal balanceToBill) {
        return TransferResponse.builder()
                .message("Success")
                .fromUserName(nameFromUser)
                .toUserName(nameToUser)
                .fromBillName(nameFromBill)
                .toBillName(nameToBill)
                .fromBillBalance(balanceFromBill)
                .toBillBalance(balanceToBill)
                .build();
    }

    private TransferResponse getErrorAddTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private TransferResponse getSuccessTransferResponse(Bill bill) {
        return TransferResponse.builder()
                .message("Success   ")
                .toBillName(bill.getBillName())
                .fromBillBalance(bill.getBalance())
                .build();
    }

    private TransferResponse getErrorTransferResponse(String message) {
        return TransferResponse.builder()
                .message(message)
                .build();
    }

    private PrintTransferResponse getSuccessPrintTransferResponse(List<Transfer> transferList) {
        return PrintTransferResponse.builder()
                .message("Success   ")
                .transferList(transferList)
                .build();
    }

    private PrintTransferResponse getErrorPrintTransferResponse(String message) {
        return PrintTransferResponse.builder()
                .message(message)
                .build();
    }
}