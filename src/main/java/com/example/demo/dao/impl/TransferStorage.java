package com.example.demo.dao.impl;


import com.example.demo.dao.StorageTransfer;
import com.example.demo.model.Transfer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferStorage implements StorageTransfer {

    private final EntityManager entityManager;

    @Override
    public Transfer add(Transfer transfer) {
        entityManager.getTransaction().begin();
        transfer.setIdFromUser(transfer.getIdFromUser());
        transfer.setIdFromBill(transfer.getIdFromBill());
        transfer.setIdToUser(transfer.getIdToUser());
        transfer.setIdToBill(transfer.getIdToBill());
        transfer.setSumTransaction(transfer.getSumTransaction());
        transfer.setTimeDateTransaction(new Timestamp(System.currentTimeMillis()));

        entityManager.persist(transfer);
        entityManager.getTransaction().commit();

        return transfer;
    }

    @Override
    public List<Transfer> getListOfElements() {

        return entityManager.createNamedQuery("Transfer.getListOfElements", Transfer.class).getResultList();

    }
}
