package com.example.demo.service;
import com.example.demo.dao.Storage;
import com.example.demo.exception.UserListException;
import com.example.demo.factory.Factory;
import com.example.demo.model.User;


import java.util.List;

public class UserService {
    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public void makeAddUser(String name) {
        userStorage.add(new User(name));
    }

    public void removeUserById(int id) {
        try {
            userStorage.remove(id) ;

        } catch (UserListException e) {

            System.err.println(e.getMessage());
                   }

    }
    public List<User> getUserList (){
    return userStorage.getListOfElements();}
}



