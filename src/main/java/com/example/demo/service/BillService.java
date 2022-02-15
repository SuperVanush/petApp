package com.example.demo.service;

import com.example.demo.dao.BillStorage;
import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;


import java.util.List;

public class BillService {

    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();


    public void addBill(String billName, int billBalance,User user) {
        Bill bill = new Bill();
        bill.setUser(user);
        bill.setBillName(billName);
        bill.setBillBalance(billBalance);
        billStorage.add(bill);
    }
    public List<Bill> getBillList (){return billStorage.getListOfElements();
    }
}
