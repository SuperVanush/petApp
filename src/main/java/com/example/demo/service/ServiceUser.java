package com.example.demo.service;

public interface ServiceUser<User> {

    User addUser(String name, String login);

    User findUserByLogin(String login);

    int removeUser(String login);

}
