package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public final class BillStorage implements Storage<Bill> {
    private final List<Bill> billList = new ArrayList<>();

    @Override
    public Bill findById(int id) throws UserListException {
        for (Bill billInList : billList) {
            if (billInList.getId() == id) {
                return billInList;
            }
        }
        throw new UserListException("Bill is not found");
    }

    @Override
    public Bill takeLastUser(Bill bill) {
        return null;
    }

    @Override
    public void add(Bill bill) {
        if (billList.isEmpty()) {
            bill.setId(1);
        } else {
            int maxId = billList.get(0).getId();
            for (Bill billInList : billList) {
                int maxNextId = billInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                bill.setId(maxId + 1);
            }
        }
        billList.add(bill);
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

