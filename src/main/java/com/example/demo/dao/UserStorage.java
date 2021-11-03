package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserStorage implements Storage<User> {

    private final List<User> userList = new ArrayList<>();

    public UserStorage() {
        userList.add(new User(1, "John"));
        userList.add(new User(2, "Mary"));
        userList.add(new User(3, "Loki"));
        userList.add(new User(4, "Thor"));
    }
    @Override
    public void add(User user) {
        int idSequence = userList.size();
        user.setId(idSequence++);
        userList.add(user);
    }

    @Override
    public void printAll() {
        userList.forEach(System.out::println);
    }

    @Override
    public List<User> getListOfElements() {
        return userList;
    }
}
