package com.example.demo.service.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceUser {
    private StorageUser userStorage;
    private BillService billService;

    public UserService(StorageUser userStorage, BillService billService) {
        this.userStorage = userStorage;
        this.billService = billService;
    }

    @Override
    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user = userStorage.add(user);
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        User userByLogin = userStorage.findByLogin(login);
        if (userByLogin != null) {
            List<Bill> bills = billService.findBillsByUser(userByLogin);
            userByLogin.setBills(bills);
        }
        return userByLogin;
    }

    @Override
    public int removeUser(String removeUserLogin) {
        User user = findUserByLogin(removeUserLogin);
        int idRemoveUser = user.getId();
        userStorage.remove(idRemoveUser);
        return idRemoveUser;
    }
}