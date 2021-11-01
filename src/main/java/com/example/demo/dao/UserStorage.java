package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserStorage implements Storage<User> {

    private final List<User> userList = new ArrayList<>();

    public UserStorage() {
        userList.add(new User(1,"John"));
        userList.add(new User(2,"Mary"));
        userList.add(new User(3,"Loki"));
        userList.add(new User(4,"Thor"));
    }

    @Override
    public void add(User user)  { // throws UserListException {
     ///   for (User userInList : userList) {
     //       if (userInList.getId() == user.getId()) {
      //          throw new UserListException("The User with ID is already in List");
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
