package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserStorage implements Storage<User> {

    private final List<User> userList = new ArrayList<>();

    public UserStorage() {

    }

    @Override
    public void add(User user) {
        int idSequence = user.getId();
        for (User userInList : userList) {
        userInList.setId(idSequence++);
        }
        userList.add(user);  }

    @Override
    public void printAll() {
        userList.forEach(System.out::println);
    }

    @Override
    public List<User> getListOfElements() {
        return userList;
    }
}
