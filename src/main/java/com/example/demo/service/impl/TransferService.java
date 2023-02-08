package com.example.demo.service.impl;

import com.example.demo.dao.impl.TransferStorage;
import com.example.demo.exception.MyExceptionTransfer;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.service.ServiceTransfer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransferService implements ServiceTransfer {

    private TransferStorage transferStorage;

    public TransferService(TransferStorage transferStorage) {
        this.transferStorage = transferStorage;
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
    public int removeTransfer(int id) {
        return 0;
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
}
