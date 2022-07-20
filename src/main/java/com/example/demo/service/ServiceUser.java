package com.example.demo.service;

import com.example.demo.model.User;

public interface ServiceUser {

    User addUser(String name, String login);

    User findUserByLogin(String login);

    int removeUser(String login);

}