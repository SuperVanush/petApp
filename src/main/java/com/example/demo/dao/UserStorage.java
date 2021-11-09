package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserStorage implements Storage<User> {

    private final List<User> userList = new ArrayList<>();

    public UserStorage() {
        userList.add(new User(1, "John"));
        userList.add(new User(2, "Mary"));
        userList.add(new User(3, "Loki"));
        userList.add(new User(77, "Thor"));
        }

    @Override
    public void add(User user) {
        int maxId = userList.get(0).getId();
        for (int i = 1; i < userList.size(); i++) {
            int maxNextId = userList.get(userList.listIterator(i).nextIndex()).getId();
            if (maxNextId > maxId)
            maxId = maxNextId;
            user.setId(maxId+1);  }
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
