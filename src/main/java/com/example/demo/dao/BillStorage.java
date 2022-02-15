package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.Bill;

import java.util.ArrayList;
import java.util.List;

public final class BillStorage implements Storage<Bill> {
    private final List<Bill> billList = new ArrayList<>();

    @Override
    public int add(Bill bill) {
        if (billList.isEmpty()) {
            bill.setBillId(1);
        } else {
            int maxId = billList.get(0).getBillId();
            for (Bill billInList : billList) {
                int maxNextId = billInList.getBillId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                bill.setBillId(maxId + 1);
            }
        }
        billList.add(bill);
        return bill.getBillId();
    }

    @Override
    public void remove(int id) throws UserListException {

    }

    @Override
    public void printAll() {

    }

    @Override
    public List<Bill> getListOfElements() {
        return billList;
    }
}

