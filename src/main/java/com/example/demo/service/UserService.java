package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        User lastUser = userStorage.add(user);
        return lastUser;
    }

    public void rewriteUser(List<Bill> bills, User lastUser) {
        lastUser.setBills(bills);
    }

    public User findUserByLogin(String login) {
        List<User> userList = getUserList();
        for (User userInList : userList) {
            if (userInList.getLogin().equals(login)) {
                return userInList;
            }
        }
        return null;
    }

    public List<User> getUserList() {
        return userStorage.getListOfElements();
    }

    public List<User> getAloneUserList(User lastUser) {
        List<User> listForAloneUser = new ArrayList<>();
        List<User> userList = userStorage.getListOfElements();
        for (User userInList : userList) {
            if (userInList.equals(lastUser)) {
                listForAloneUser.add(userInList);
            }
        }
        return listForAloneUser;
    }
}



