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
    UserStorage userStorage = new UserStorage();



    public void addBill(String name, int balance,int id, User user) throws BillListExсeption,UserListException{
        Bill bill = new Bill();
        User userStorageById = userStorage.findById(id);
        bill.setUser(userStorageById);
        bill.setName(name);
        bill.setBalance(balance);
        billStorage.add(bill);
    }
    public List<Bill> getBillList (){return billStorage.getListOfElements();
    }
}

