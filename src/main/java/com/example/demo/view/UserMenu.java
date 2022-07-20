package com.example.demo.view;

import com.example.demo.factory.Factory;
import com.example.demo.model.User;
import com.example.demo.service.implservice.UserService;

import java.util.Scanner;

public class UserMenu {

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    private final Scanner in = new Scanner(System.in);
    private final UserService userService = Factory.getUserServiceInstance();
    private final BillMenu billMenu = Factory.getBillMenuInstance();

    public void setRegistration() {
        String name;
        String login;
        System.out.println("Input name of user");
        name = in.next();
        System.out.println("Enter User login");
        login = in.next();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            userService.addUser(name, login);
            System.out.println("The User was Added");
        } else {
            System.out.println("The user exists. Choose another login");
        }
    }

    public void setWorkInCabinet() {
        System.out.println("Enter User login");
        String login = in.next();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            System.out.println("User not found. Please go to Registration");
        } else {
            int userMenuChouce;
            do {
                System.out.println("1. Print User");
                System.out.println("2. Go to Bills menu");
                System.out.println(PRINT_MAIN_MENU);
                userMenuChouce = in.nextInt();
                if (userMenuChouce == 1) {
                    System.out.println(user);
                }
                if (userMenuChouce == 2) {
                    billMenu.enterBillMenu(user);
                }
                if (userMenuChouce != 1 && userMenuChouce != 0 && userMenuChouce != 2) {
                    System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                }
            }
            while (userMenuChouce != 0);
        }
    }

    public void removeUser() {
        System.out.println("Enter login of user to remove");
        String loginRemoveUser = in.next();
        userService.removeUser(loginRemoveUser);
        System.out.println("User was removed");
    }
}