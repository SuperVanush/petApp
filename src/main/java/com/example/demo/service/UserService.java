package com.example.demo.service;

import com.example.demo.dao.StorageBill;
import com.example.demo.dao.StorageUser;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final StorageUser<User> userStorage = Factory.getUserStorageInstance();
    private final StorageBill<Bill> billStorage = Factory.getBillStorageInstance();

    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user = userStorage.add(user);
        return user;
    }

    public User rewriteUser(List<Bill> bills, User lastUser) {
        lastUser.setBills(bills);
        return lastUser;
    }

    public User findUserByLogin(String login) {
        List<User> userList = userStorage.getListOfElements();
        for (User userInList : userList) {
            if (userInList.getLogin().equals(login)) {
                int idUserInList = userInList.getId();
                List<Bill> billsList = billStorage.getListOfElements();
                List<Bill> newBillList = new ArrayList<>();
                for (Bill billInList : billsList) {
                    if (billInList.getUser().getId() == idUserInList) {
                        newBillList.add(billInList);
                    }
                    userInList.setBills(newBillList);
                }
                return userInList;
            }
        }
        return null;
    }

    public int removeUser(String removeUserLogin) {
        User user = findUserByLogin(removeUserLogin);
        int idRemoveUser = user.getId();
        userStorage.remove(idRemoveUser);
        return idRemoveUser;
    }
}