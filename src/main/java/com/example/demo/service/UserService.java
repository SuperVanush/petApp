package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user = userStorage.add(user);
        return user;
    }

    public void rewriteUser(List<Bill> bills, User lastUser) {
        lastUser.setBills(bills);
    }

    public User findUserByLogin(String login) {
        User user = userStorage.findByLogin(login);
        return user;
    }
}