package com.example.demo.dao;

import java.util.List;

public interface StorageUser<User> {
    User add(User user);

    User findById(int id);

    void printAll();

    List<User> getListOfElements();

    void remove(int id);

}
