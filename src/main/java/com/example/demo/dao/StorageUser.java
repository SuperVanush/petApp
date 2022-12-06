package com.example.demo.dao;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StorageUser {
    User add(User user);

    User findById(int id);

    User findByLogin(String login);

    List<User> getListOfElements();

    void remove(int id);
}