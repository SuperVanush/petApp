package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.sort.User;

import java.util.ArrayList;
import java.util.List;

public class UserStorage implements Storage<User> {

    private final List<User> userList = new ArrayList<>();

    @Override
    public void add(User user) throws UserListException {
        for (User userInList : userList) {
            if (userInList.getId() == user.getId()) {
                throw new UserListException("The User with ID is already in List");
            }
        }
        userList.add(user);
    }

    @Override
    public void remove(int i) throws UserListException {
        for (User user : userList) {
            if (user.getId() != i) {
                throw new UserListException("There is no User with this ID");
            }
        }
        userList.removeIf(user -> user.getId() == i);
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
