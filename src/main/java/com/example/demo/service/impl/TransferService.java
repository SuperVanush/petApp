package com.example.demo.service.impl;

import com.example.demo.exception.TransferException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.ServiceTransfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService implements ServiceTransfer {

    private final TransferRepository transferRepository;
    private final BillRepository billRepository;

    @Override
    public Transfer addTransfer(User lastUser, User toUser, int idFromBill, int idToBill, BigDecimal transactionSumma) {
        Transfer transfer = Transfer.builder()
                .idFromUser(lastUser.getId())
                .idToUser(toUser.getId())
                .idFromBill(idFromBill)
                .idToBill(idToBill)
                .sumTransaction(transactionSumma)
                .build();
        transferRepository.save(transfer);

        return transfer;
    }

    @Override
    public List<Transfer> findTransferByBillsId(int id) {
        List<Transfer> transferList = transferRepository.getListOfElements();
        List<Transfer> transferListForReturn = transferList
                .stream()
                .filter(transfer -> id == transfer.getIdFromBill())
                .collect(Collectors.toList());

        return transferListForReturn;
    }

    @Override
    public Bill sumBalanceTransaction(int idBill, BigDecimal sumDigit) {
        Bill bill = billRepository.findBillById(idBill).get();
        BigDecimal billBalance = bill.getBalance();
        BigDecimal sumBillBalance = billBalance.add(sumDigit);
        bill.setBalance(sumBillBalance);
        billRepository.updateBill(bill);

        return bill;
    }

    @Override
    public Bill reduceBalance(int idBill, BigDecimal reduceDigit) {
        Bill bill = billRepository.findBillById(idBill).get();
        BigDecimal billBalance = bill.getBalance();
        BigDecimal reduceBillBalance = billBalance.subtract(reduceDigit);
        if (reduceBillBalance.compareTo(BigDecimal.ZERO) > 0) {
            bill.setBalance(reduceBillBalance);
            billRepository.updateBill(bill);
        } else {
            throw new TransferException("TRY AGAIN YOUR BALANCE IS MINUS");
        }
        return bill;
    }

    @Override
    public void transactionToBill(int idFromBill, int idToBill, BigDecimal transactionSumma) throws TransferException {
        reduceBalance(idFromBill, transactionSumma);
        sumBalanceTransaction(idToBill, transactionSumma);
    }
}