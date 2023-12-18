package com.example.demo.service;

import com.example.demo.model.User;

public interface ServiceUser {

    User addUser(String name, String login, String password);

    User findUserByLogin(String login);

    String removeUser(String login);
}