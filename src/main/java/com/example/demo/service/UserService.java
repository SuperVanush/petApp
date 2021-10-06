package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;

public class UserService {
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void makeAddUser(int id, String name) {
        try {
            userStorage.add(new User(id, name));
        } catch (UserListException e) {
            System.out.println(e.getMessage());
        }
    }
}



