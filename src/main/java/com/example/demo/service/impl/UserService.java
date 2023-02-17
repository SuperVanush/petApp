package com.example.demo.service.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements ServiceUser {

    private StorageUser userStorage;
    private BillService billService;

    public UserService(StorageUser userStorage, BillService billService) {
        this.userStorage = userStorage;
        this.billService = billService;
    }

    @Override
    public User addUser(String name, String login, String password) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user.setPassword(password);
        user = userStorage.add(user);
        return user;
    }

    @Override
    public User findUserByLogin(String login) throws MyExceptionUser {
        User userByLogin = userStorage.findByLogin(login);
        if (userByLogin == null) {
            throw new MyExceptionUser("User not found. Please enter other User");
        }
        return userByLogin;
    }

    @Override
    public User findUserByPassword(String password) throws MyExceptionUser {
        User userByPassword = userStorage.findByPassword(password);
        if (userByPassword == null) {
            throw new MyExceptionUser("Wrong password");
        }
        return userByPassword;
    }

    @Override
    public User findUserById(int idUser) {
        User userById = userStorage.findById(idUser);
        if (userById != null) {
            List<Bill> bills = billService.findBillsByUser(userById);
            userById.setBills(bills);
        }
        return userById;
    }

    @Override
    public int removeUser(String removeUserLogin) {
        User user = findUserByLogin(removeUserLogin);
        int idRemoveUser = user.getId();
        userStorage.remove(idRemoveUser);
        return idRemoveUser;
    }
}