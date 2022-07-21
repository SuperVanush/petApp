package com.example.demo.service.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceBill;

import java.util.ArrayList;
import java.util.List;

public class BillService implements ServiceBill {

    private final StorageBill billStorage = Factory.getBillStorageInstance();

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

    @Override
    public Bill sumBalanceTransaction(int idBill, int sumDigit) {
        Bill bill = billStorage.getBillFromId(idBill);
        int billBalance = bill.getBalance();
        int sumBillBalance = billBalance + sumDigit;
        bill.setBalance(sumBillBalance);
        billStorage.changeBalanсe(bill);
        return bill;
    }

    @Override
    public Bill reduceBalance(int idBill, int reduceDigit) {
        Bill bill = billStorage.getBillFromId(idBill);
        int billBalance = bill.getBalance();
        int reduceBillBalance = billBalance - reduceDigit;
        if (reduceBillBalance < 0) {
            return null;
        } else {
            bill.setBalance(reduceBillBalance);
            billStorage.changeBalanсe(bill);
        }
        return bill;
    }
}
