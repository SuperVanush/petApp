package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.RequestType;
import com.example.demo.service.ServiceTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;
    private final UserRepository userRepository;
    private final BillService billService;

    public Transfer addTransfer(User lastUser, User toUser, String nameFromBill, String nameToBill, BigDecimal transactionSumma) {
        Bill fromBill = billService.findBillByName(nameFromBill);
        Bill toBill = billService.findBillByName(nameToBill);
        Transfer transfer = Transfer.builder().fromUser(lastUser).toUser(toUser).fromBill(fromBill).toBill(toBill).sumTransaction(transactionSumma).timeDateTransaction(new Timestamp(System.currentTimeMillis())).build();
        transferRepository.save(transfer);
        return transfer;
    }

    public TransferResponse transfer(TransferRequest request) {
        if ((RequestType.SUM == request.getRequestType())) {
            return sumTransaction(request);
        }
        if (RequestType.REDUCE == request.getRequestType()) {
            return reduceTransaction(request);
        }
        if (RequestType.TRANSFER == request.getRequestType()) {
            return transactionBetweenBill(request);
        } else {
            return TransferResponse.builder().message("Wrong RequestType").build();
        }
    }

    public TransferResponse reduceTransaction(TransferRequest request) {
        try {
            String billName = request.getNameFromBill();
            BigDecimal reduceDigit = request.getSumTransfer();
            String login = request.getLoginToUser();
            User userByLogin = userRepository.findByLogin(login).orElseThrow(() -> new UserNotFoundException("User not found by login = " + login));
            String nameFromBill = reduceBalance(billName, reduceDigit).getFromBill().getBillName();
            Transfer transfer = addTransfer(userByLogin, userByLogin, nameFromBill, nameFromBill, reduceDigit);
            return getSuccessTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorTransferResponse(e.getMessage());
        }
    }

    public TransferResponse sumTransaction(TransferRequest request) {
        try {
            String loginFromUser = request.getLoginFromUser();
            User user = userRepository.findByLogin(loginFromUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginFromUser));

            String nameToBillRequest = request.getNameToBill();
            BigDecimal sumTransfer = request.getSumTransfer();
            Bill toBill = sumBalanceTransaction(nameToBillRequest, sumTransfer).getToBill();
            String nameToBill = toBill.getBillName();
            Transfer transfer = addTransfer(user, user, nameToBill, nameToBill, sumTransfer);
            return getSuccessAddTransferResponse(transfer.getId());
        } catch (TransferException e) {
            return getErrorAddTransferResponse(e.getMessage());
        }
    }

    @Override
    public PrintTransferResponse findTransferByBillsName(PrintTransferRequest request) {
        try {
            String billName = request.getBillName();
            Bill bill = billService.findBillByName(billName);
            List<Transfer> transferList = transferRepository.findTransferByFromBill(bill);
            return getSuccessPrintTransferResponse(transferList);
        } catch (UserNotFoundException e) {
            return getErrorPrintTransferResponse(e.getMessage());
        }
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

    public TransferResponse transactionBetweenBill(TransferRequest request) throws TransferException {
        try {
            String loginFromUser = request.getLoginFromUser();
            String loginToUser = request.getLoginToUser();
            User fromUser = userRepository.findByLogin(loginFromUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginFromUser));
            User toUser = userRepository.findByLogin(loginToUser).orElseThrow(() -> new UserNotFoundException("User not found by login = " + loginToUser));
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