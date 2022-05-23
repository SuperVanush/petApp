package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class BillService {
    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();
    UserService userService = new UserService();

    public void addBill(String billName, int billBalance, User lastUser) {
        Bill bill = new Bill();
        bill.setName(billName);
        bill.setBalance(billBalance);
        bill.setUser(lastUser);
        billStorage.add(bill);
        List<Bill> bills = findBillsByUser(lastUser);
        userService.rewriteUser(bills, lastUser);
    }

    public List<Bill> findBillsByUser(User lastUser) {
        List<Bill> billsList = new ArrayList<>();
        List<Bill> billList = billStorage.getListOfElements();
        for (Bill billInList : billList) {
            if (billInList.getUser().equals(lastUser)) {
                billsList.add(billInList);
            }
        }
        return billsList;
    }
    public List <Bill> getBillList () {
        return billStorage.getListOfElements();
    }
}
