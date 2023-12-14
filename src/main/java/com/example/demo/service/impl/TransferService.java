package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.RequestType;
import com.example.demo.service.ServiceTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final BillService billService;
    private final UserService userService;

    @Override
    public Transfer addTransfer(User lastUser, User toUser, String nameFromBill, String nameToBill, BigDecimal transactionSumma) {
        Bill fromBill = billService.findBillByName(nameFromBill);
        Bill toBill = billService.findBillByName(nameToBill);
        Transfer transfer = Transfer.builder()
                .fromUser(lastUser)
                .toUser(toUser)
                .fromBill(fromBill)
                .toBill(toBill)
                .sumTransaction(transactionSumma)
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public TransferResponse transfer(TransferRequest request) {
        if ((RequestType.SUM == request.getRequestType())) {
            return sumTransaction(request);
        }
        if (RequestType.REDUCE == request.getRequestType()) {
            return reduceTransaction(request);
        } else {
            return TransferResponse.builder().message("Wrong RequestType").build();
        }
    }

    @Override
    public TransferResponse reduceTransaction(TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String userLogin = request.getLoginToUser();
            User user = userService.findUserByLogin(userLogin);
            String nameFromBill = reduceBalance(billName, reduceDigit).getFromBill().getBillName();

            Transfer transfer = addTransfer(user, user, nameFromBill, nameFromBill, reduceDigit);
            return getSuccessTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    @Override
    public TransferResponse sumTransaction(TransferRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            User user = userService.findUserByLogin(loginFromUser);

            String nameToBillRequest = request.getNameToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            String nameToBill = sumBalanceTransaction(nameToBillRequest, sumTransfer).getToBill().getBillName();

            Transfer transfer = addTransfer(user, user, nameToBill, nameToBill, sumTransfer);
            return getSuccessAddTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }

    }

    @Override
    public List<Transfer> findTransferByBillsName(String billName) {
        int billId = billService.findBillByName(billName).getId();
        List<Transfer> transferList = transferRepository.findAll();
        return transferList
                .stream()
                .filter(transfer -> billId == transfer.getFromBill().getId())
                .collect(Collectors.toList());

    }

    @Override
    public Transfer sumBalanceTransaction(String toBillName, BigDecimal sumDigit) {
        Bill toBill = billRepository.findBillByBillName(toBillName).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = toBill.getBalance();
        BigDecimal sumBillBalance = billBalance.add(sumDigit);
        toBill.setBalance(sumBillBalance);
        billRepository.save(toBill);
        Transfer toTransfer = new Transfer();
        toTransfer.setToBill(toBill);
        return toTransfer;

    }

    @Override
    public Transfer reduceBalance(String nameFromBill, BigDecimal reduceDigit) {
        Bill fromBill = billRepository.findBillByBillName(nameFromBill).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = fromBill.getBalance();
        BigDecimal reduceBillBalance = billBalance.subtract(reduceDigit);
        Transfer fromTransfer = new Transfer();
        if (reduceBillBalance.compareTo(BigDecimal.ZERO) > 0) {
            fromBill.setBalance(reduceBillBalance);
            billRepository.save(fromBill);
            fromTransfer.setFromBill(fromBill);
        } else {
            throw new TransferException("TRY AGAIN YOUR BALANCE IS MINUS");
        }

        return fromTransfer;
    }

    @Override
    public TransferResponse transactionBetweenBill(TransferRequest request) throws TransferException {
        try {
            User fromUser = userService.findUserByLogin(request.getLoginFromUser());
            User toUser = userService.findUserByLogin(request.getLoginToUser());
            String nameFromBill = request.getNameFromBill();
            String nameToBill = request.getNameToBill();
            BigDecimal transactionSum = request.getSumTransfer();
            Bill fromBill = reduceBalance(nameFromBill, transactionSum).getFromBill();
            Bill toBill = sumBalanceTransaction(nameToBill, transactionSum).getToBill();
            Transfer transfer = Transfer.builder()
                    .fromUser(fromUser)
                    .toUser(toUser)
                    .fromBill(fromBill)
                    .toBill(toBill)
                    .sumTransaction(transactionSum)
                    .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                    .build();
            transferRepository.save(transfer);
            return getSuccessAddTransferResponse(transfer.getId());

        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
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
}