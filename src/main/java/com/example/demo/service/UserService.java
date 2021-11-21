package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;
import lombok.SneakyThrows;

public class UserService {
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    @SneakyThrows
    public void makeAddUser(String name) {
        userStorage.add(new User(name));
    }

    public void removeUserById(int id) {
        try {
            userStorage.remove(id);
        } catch (UserListException e) {
            System.err.println("not found ID");

        }
    }
}



