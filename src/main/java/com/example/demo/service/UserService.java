package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
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



