package com.example.demo.service.impl;

import com.example.demo.dao.impl.UserStorage;
import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceUser {

    private final UserStorage userStorage;
    private final BillService billService;

    @Override
    public User addUser(String name, String login, String password) {
        User findUserByLogin = userStorage.findByLogin(login);
        if (findUserByLogin != null) {
            throw new MyExceptionUser("User with this login exist. Enter another login");
        }
        User user = User.builder()
                .username(name)
                .login(login)
                .password(password)
                .build();
        user = userStorage.add(user);
        return user;
    }

    @Override
    public User findUserByLogin(String login) {
        User userByLogin = userStorage.findByLogin(login);
        if (userByLogin == null) {
            throw new MyExceptionUser("User not found. Please enter other User");
        }
        return userByLogin;
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