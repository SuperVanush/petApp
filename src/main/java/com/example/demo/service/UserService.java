package com.example.demo.service;

import com.example.demo.dao.StorageUser;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.List;

public class UserService {

    private final StorageUser<User> userStorage = Factory.getUserStorageInstance();
    private final BillService billService = Factory.getBillServiceInstance();

    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user = userStorage.add(user);
        return user;
    }

    public User findUserByLogin(String login) {
        User userByLogin = userStorage.findByLogin(login);
        if (userByLogin != null) {
            List<Bill> bills = billService.findBillsByUser(userByLogin);
            userByLogin.setBills(bills);
        }
        return userByLogin;
    }

    public int removeUser(String removeUserLogin) {
        User user = findUserByLogin(removeUserLogin);
        int idRemoveUser = user.getId();
        userStorage.remove(idRemoveUser);
        return idRemoveUser;
    }
}