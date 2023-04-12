package com.example.demo.service.impl;

import com.example.demo.dao.impl.BillStorage;
import com.example.demo.dao.impl.TransferStorage;
import com.example.demo.exception.MyExceptionBill;
import com.example.demo.exception.MyExceptionTransfer;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.service.ServiceTransfer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService implements ServiceTransfer {

    private TransferStorage transferStorage;
    private BillStorage billStorage;

    public TransferService(TransferStorage transferStorage, BillStorage billStorage) {
        this.transferStorage = transferStorage;
        this.billStorage = billStorage;
    }

    @Override
    public Transfer addTransfer(User lastUser, User toUser, int idFromBill,
                                int idToBill, BigDecimal transactionSumma) {
        Transfer transfer = new Transfer();
        transfer.setIdFromUser(lastUser.getId());
        transfer.setIdToUser(toUser.getId());
        transfer.setIdFromBill(idFromBill);
        transfer.setIdToBill(idToBill);
        transfer.setSumTransaction(transactionSumma);
        transferStorage.add(transfer);
        return transfer;
    }

    @Override
    public List<Transfer> findTransferByBillsId(int id) throws MyExceptionTransfer {
        List<Transfer> transferListForReturn = new ArrayList<>();
        List<Transfer> transferList = transferStorage.getListOfElements();
        for (Transfer transferInList : transferList) {
            if (transferInList.getIdFromBill() == id) {
                transferListForReturn.add(transferInList);
            }
        }
        return transferListForReturn;
    }

    @Override
    public Bill sumBalanceTransaction(int idBill, BigDecimal sumDigit) {
        Bill bill = billStorage.findBillFromId(idBill);
        BigDecimal billBalance = bill.getBalance();
        BigDecimal sumBillBalance = billBalance.add(sumDigit);
        bill.setBalance(sumBillBalance);
        billStorage.updateBill(bill);
        return bill;
    }

    @Override
    public Bill reduceBalance(int idBill, BigDecimal reduceDigit) throws MyExceptionBill {
        Bill bill = billStorage.findBillFromId(idBill);
        BigDecimal billBalance = bill.getBalance();
        BigDecimal reduceBillBalance = billBalance.subtract(reduceDigit);
        if (reduceBillBalance.compareTo(BigDecimal.ZERO) > 0) {
            bill.setBalance(reduceBillBalance);
            billStorage.updateBill(bill);
        } else {
            throw new MyExceptionBill("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
        }
        return bill;
    }

    @Override
    public void transactionToBill(int idFromBill, int idToBill, BigDecimal transactionSumma) throws MyExceptionBill {
        reduceBalance(idFromBill, transactionSumma);
        sumBalanceTransaction(idToBill, transactionSumma);
    }
}