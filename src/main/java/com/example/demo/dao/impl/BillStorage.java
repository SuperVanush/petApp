package com.example.demo.dao.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BillStorage implements StorageBill {

    EntityManager entityManager;
    EntityTransaction entityTransaction;

    public BillStorage(@Qualifier("createEntityManager") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Bill add(Bill bill) {
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        bill.setName(bill.getName());
        bill.setBalance(bill.getBalance());
        bill.setUser(bill.getUser());
        entityManager.persist(bill);

        return bill;
    }

    @Override
    public List<Bill> getListOfElements() {
        return entityManager.createNamedQuery("Bill.getListOfElements", Bill.class)
                .getResultList();
    }

    @Override
    public Bill findBillFromId(int idBill) {
        return entityManager.createNamedQuery("Bill.findById", Bill.class)
                .setParameter(idBill, idBill)
                .getSingleResult();
    }

    @Override
    public void updateBill(Bill bill) {
        BigDecimal balanceBill = bill.getBalance();
        int idBill = bill.getId();
        int update = entityManager.createNamedQuery("Bill.updateBill", Bill.class)
                .setParameter(idBill, idBill)
                .executeUpdate();
    }
}