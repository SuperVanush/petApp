package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void addUser(String name, String billName, int billBalance) {
        Bill bill = new Bill(billName,billBalance);
        User user = new User(name);
        userStorage.add(user,bill);
    }
      public void addSeveralUsers(String severalUsers) {
        String[] listSeveralUsers = severalUsers.split(",");
        for (String name : listSeveralUsers) {
            User user = new User(name);
            userStorage.add(user,new Bill(billName,billBalance));
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



