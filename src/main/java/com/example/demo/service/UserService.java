package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();


    public User addUser(String name) {
        User user = new User(name);
        user.setName(name);
        User lastUser = userStorage.add(user);
        return lastUser;
    }

    public void rewriteUser(Bill lastBill, User lastUser) {
        lastUser.setBill(lastBill);
    }

    public void addSeveralUsers(String severalUsers) {
        String[] listSeveralUsers = severalUsers.split(",");
        for (String name : listSeveralUsers) {
            User user = new User(name);
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



