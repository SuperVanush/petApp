package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class BillService {

    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();
    private final UserService userService = Factory.getUserServiceInstance();

    public void addBill(String billName, int billBalance, User findUser) {
        Bill bill = new Bill();
        bill.setName(billName);
        bill.setBalance(billBalance);
        bill.setUser(findUser);
        billStorage.add(bill);
        List<Bill> bills = findBillsByUser(findUser);
        userService.rewriteUser(bills, findUser);
    }

    public List<Bill> findBillsByUser(User findUser) {
        List<Bill> billsList = new ArrayList<>();
        List<Bill> billList = billStorage.getListOfElements();
        for (Bill billInList : billList) {
            if (billInList.getUser().equals(findUser)) {
                billsList.add(billInList);
            }
        }
        return billsList;
    }
}
