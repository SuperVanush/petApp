package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;
import lombok.SneakyThrows;

public class UserService {
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    @SneakyThrows
    public void makeAddUser(String name) {
        userStorage.add(new User(name));
       }
    }



