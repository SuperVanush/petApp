package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void addUser(String name, String billName, int billBalance) {
        Bill bill = new Bill();
        bill.setBillName(billName);
        bill.setBillBalance(billBalance);
        List <Bill> billList= new ArrayList<>();
        User user = new User();
        user.setName(name);
        user.setBillList(billList);

        userStorage.add(user);
    }
      public void addSeveralUsers(String severalUsers) {
        String[] listSeveralUsers = severalUsers.split(",");
        for (String name : listSeveralUsers) {
            User user = new User();
            user.setName(name);
             userStorage.add(user);
        }
    }
    public void removeUserById(int id) {
        try {
            userStorage.remove(id);
        } catch (UserListException e) {
            System.err.println(e.getMessage());
        }
    }
    public List<User> getUserList() {
        return userStorage.getListOfElements();
    }
}



