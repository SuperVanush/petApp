package com.example.demo.dao.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
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