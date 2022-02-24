package com.example.demo.service;

import com.example.demo.dao.BillStorage;
import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.exception.BillListExсeption;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;


import java.util.List;

public class BillService {

    private final Storage<Bill> billStorage = Factory.getBillStorageInstance();
    private final Storage<User> userStorage = Factory.getUserStorageInstance();


    public void addBill(String billname, int balance,int id, User user) {
        Bill bill = new Bill();
        User userStorageById = null;
        try {
            userStorageById = userStorage.findById(id);
        } catch (UserListException e) {
           System.out.println(e.getMessage());
        }
           bill.setUser(userStorageById);
        bill.setName(billname);
        bill.setBalance(balance);
        billStorage.add(bill);

    }
    public List<Bill> getBillList (){return billStorage.getListOfElements();
    }
}

