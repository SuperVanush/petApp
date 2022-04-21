package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class BillStorage implements Storage<Bill> {
    private final List<Bill> biilList = new ArrayList<>();


    @Override
    public int add(Bill bill) {
        if (biilList.isEmpty()) {
            bill.setId(1);
        } else {
            int maxId = biilList.get(0).getId();
            for (Bill billInList : biilList) {
                int maxNextId = billInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                bill.setId(maxId + 1);
            }
        }
        biilList.add(bill);
        int Id = bill.getId();
        return Id;
    }

    @Override
    public Bill findById(int id) throws UserListException {
        for (Bill billInList : biilList) {
            if (billInList.getId() == id) {
                return billInList;
            }
        }
        throw new UserListException("User is not found");
    }

    @Override
    public void remove(int id) throws UserListException {

    }

    @Override
    public void printAll() {

    }

    @Override
    public List<Bill> getListOfElements() {
        return null;
    }
}
