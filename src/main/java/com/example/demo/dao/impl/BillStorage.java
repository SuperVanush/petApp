package com.example.demo.dao.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import com.example.demo.service.impl.BillService;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.util.List;

@Service
@Data
public class BillStorage implements StorageBill {
    private BillService billService;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

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
        List<Bill> billList = entityManager.createNamedQuery("Bill.getListOfElements", Bill.class)
                .getResultList();
        return billList;
    }

    @Override
    public Bill findBillFromId(int idBill) {
        Bill bill = entityManager.createNamedQuery("Bill.findById", Bill.class)
                .setParameter(idBill, Integer.valueOf(idBill))
                .getSingleResult();
        return bill;
    }

    @Override
    public void updateBill(Bill bill) {
        BigDecimal balanceBill = bill.getBalance();
        int idBill = bill.getId();
        int update = entityManager.createNamedQuery("Bill.updateBill", Bill.class)
                .setParameter(idBill, Integer.valueOf(idBill))
                .executeUpdate();
    }
}