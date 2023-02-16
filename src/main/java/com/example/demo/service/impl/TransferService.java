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
                                int idToBill, int transactionSumma) {
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
    public Bill sumBalanceTransaction(int idBill, int sumDigit) {
        Bill bill = billStorage.findBillFromId(idBill);
        int billBalance = bill.getBalance();
        int sumBillBalance = billBalance + sumDigit;
        bill.setBalance(sumBillBalance);
        billStorage.updateBill(bill);
        return bill;
    }

    @Override
    public Bill reduceBalance(int idBill, int reduceDigit) throws MyExceptionBill {
        Bill bill = billStorage.findBillFromId(idBill);
        int billBalance = bill.getBalance();
        int reduceBillBalance = billBalance - reduceDigit;
        if (reduceBillBalance < 0) {
            throw new MyExceptionBill("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
        } else {
            bill.setBalance(reduceBillBalance);
            billStorage.updateBill(bill);
        }
        return bill;
    }

    @Override
    public void transactionToBill(int idFromBill, int idToBill, int transactionSumma) throws MyExceptionBill {
        reduceBalance(idFromBill, transactionSumma);
        sumBalanceTransaction(idToBill, transactionSumma);
    }

    @Override
    public int removeTransfer(int id) {
        return 0;
    }

}
