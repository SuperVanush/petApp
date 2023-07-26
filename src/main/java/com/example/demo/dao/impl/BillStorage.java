package com.example.demo.dao.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillStorage implements StorageBill {

    private final EntityManager entityManager;

    @Override
    public Bill add(Bill bill) {
        entityManager.getTransaction().begin();

        bill.setBillName(bill.getBillName());
        bill.setBalance(bill.getBalance());
        bill.setUser(bill.getUser());
        entityManager.persist(bill);
        entityManager.getTransaction().commit();

        return bill;
    }

    @Override
    public List<Bill> getListOfElements() {
        return entityManager.createNamedQuery("Bill.getListOfElements", Bill.class)
                .getResultList();
    }

    @Override
    public Bill findBillFromId(int id) {
        return entityManager.createNamedQuery("Bill.findById", Bill.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void updateBill(Bill bill) {
        entityManager.getTransaction().begin();
        entityManager.merge(bill);
        entityManager.getTransaction().commit();
    }
}