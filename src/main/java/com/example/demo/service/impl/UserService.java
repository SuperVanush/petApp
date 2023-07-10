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
        if (userStorage.findByLogin(login).isPresent()) {
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
        return userStorage.findByLogin(login)
                .orElseThrow(()-> new MyExceptionUser("User not found. Enter other user"));
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
    public User removeUser(String removeUserLogin) {
        User user = findUserByLogin(removeUserLogin);
        userStorage.remove(user);

        return user;
    }
}