package com.example.demo.service;


import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;


import java.util.List;

public class BillService {

    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();
    private final Storage<User> userStorage = Factory.getUserStorageInstance();


    public void addBill(String billname, int balance, int id, User user, int idLastUser) {
        Bill bill = new Bill();
        bill.setName(billname);
        bill.setBalance(balance);
        try {
            userStorage.findById(idLastUser);
        } catch (UserListException e) {
            System.err.println(e.getMessage());
        }
        bill.setUser(user);
        billStorage.add(bill);

    }

    public List<Bill> getBillList() {
        return billStorage.getListOfElements();
    }
}

