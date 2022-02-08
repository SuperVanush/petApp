package com.example.demo.service;

import com.example.demo.dao.BillStorage;
import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;

public class BillService {

    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();

    public void addBill(String billName, int billBalance, int billId) {
        Bill bill = new Bill();
        bill.setBillName(billName);
        bill.setBillBalance(billBalance);
        billStorage.add(bill);
    }
}
