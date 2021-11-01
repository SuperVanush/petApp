package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;

public class UserService {
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void makeAddUser(int id, String name) {
                   userStorage.add(new User(id, name));
       }
    }



