package com.example.demo.service;

import com.example.demo.dao.StorageBill;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class BillService implements ServiceBill<Bill> {

    private final StorageBill<Bill> billStorage = Factory.getBillStorageInstance();

    @Override
    public void addBill(String billName, int billBalance, User user) {
        Bill bill = new Bill();
        bill.setName(billName);
        bill.setBalance(billBalance);
        bill.setUser(user);
        billStorage.add(bill);
    }

    @Override
    public List<Bill> findBillsByUser(User findUser) {
        List<Bill> billsList = new ArrayList<>();
        List<Bill> billList = billStorage.getListOfElements();
        for (Bill billInList : billList) {
            int idUser = findUser.getId();
            if (billInList.getUser().getId() == idUser) {
                billsList.add(billInList);
            }
        }
        return billsList;
    }
}