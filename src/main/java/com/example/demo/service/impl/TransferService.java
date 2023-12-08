package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
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
        int idFromBill = billService.findBillByName(nameFromBill).getId();
        int idToBill = billService.findBillByName(nameToBill).getId();
        Transfer transfer = Transfer.builder()
                .idFromUser(lastUser.getId())
                .idToUser(toUser.getId())
                .idFromBill(idFromBill)
                .idToBill(idToBill)
                .sumTransaction(transactionSumma)
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public List<Transfer> findTransferByBillsName(String billName) {
        int billId = billService.findBillByName(billName).getId();
        List<Transfer> transferList = transferRepository.findAll();
        List<Transfer> transferListForReturn = transferList
                .stream()
                .filter(transfer -> billId == transfer.getIdFromBill())
                .collect(Collectors.toList());

        return transferListForReturn;
    }

    @Override
    public Bill sumBalanceTransaction(String fromBillName, BigDecimal sumDigit) {
        Bill bill = billRepository.findBillByBillName(fromBillName).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = bill.getBalance();
        BigDecimal sumBillBalance = billBalance.add(sumDigit);
        bill.setBalance(sumBillBalance);
        billRepository.save(bill);

        return bill;
    }

    @Override
    public Bill reduceBalance(String nameFromBill, BigDecimal reduceDigit) {
        Bill bill = billRepository.findBillByBillName(nameFromBill).orElseThrow(() -> new UserNotFoundException("Bill not found"));
        BigDecimal billBalance = bill.getBalance();
        BigDecimal reduceBillBalance = billBalance.subtract(reduceDigit);

        if (reduceBillBalance.compareTo(BigDecimal.ZERO) > 0) {
            bill.setBalance(reduceBillBalance);
            billRepository.save(bill);
        } else {
            throw new TransferException("TRY AGAIN YOUR BALANCE IS MINUS");
        }

        return bill;
    }

    @Override
    public Transfer transactionBetweenBill(String fromUserLogin, String toUserLogin, String nameFromBill, String nameToBill, BigDecimal transactionSumma) throws TransferException {
        int idFromUser = userService.findUserByLogin(fromUserLogin).getId();
        int idToUser = userService.findUserByLogin(toUserLogin).getId();
        int idFromBill = reduceBalance(nameFromBill, transactionSumma).getId();
        int idToBill = sumBalanceTransaction(nameToBill, transactionSumma).getId();
        Transfer transfer = Transfer.builder()
                .idFromUser(idFromUser)
                .idToUser(idToUser)
                .idFromBill(idFromBill)
                .idToBill(idToBill)
                .sumTransaction(transactionSumma)
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);
        return transfer;
    }
}