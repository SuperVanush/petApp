package com.example.demo.view;

import com.example.demo.factory.Factory;
import com.example.demo.service.UserService;

import java.util.Scanner;

public class UserMenu {

    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = Factory.getUserServiceInstance();

    public String getLogin() {
        String login;
        System.out.println("Enter User login");
        login = in.next();
        return login;
    }

    public void removeUser() {
        System.out.println("Enter login of user to remove");
        String loginRemoveUser = in.next();
        userService.removeUser(loginRemoveUser);
        System.out.println("User was removed");
    }
}
