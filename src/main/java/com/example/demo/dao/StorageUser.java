package com.example.demo.dao;

import com.example.demo.model.User;

import java.util.List;

public interface StorageUser {
    User add(User user);

    User findById(int id);

    User findByLogin(String login);

    List<User> getListOfElements();

    void remove(int id);
}