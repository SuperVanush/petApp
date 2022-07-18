package com.example.demo.service;

import com.example.demo.dao.StorageUser;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.List;

public class UserService {

    private final StorageUser<User> userStorage = Factory.getUserStorageInstance();
    private BillService billService;

    public BillService getBillService() {
        billService = Factory.getBillServiceInstance();
        return billService;
    }

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
        User userByLogin = userStorage.findByLogin(login);
        return userByLogin;
    }

    public User printUser(String login) {
        List<User> userList = userStorage.getListOfElements();
        for (User userInList : userList) {
            if (userInList.getLogin().equals(login)) {
                List<Bill> bills = getBillService().findBillsByUser(userInList);
                userInList.setBills(bills);
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