package com.example.demo.view;

import com.example.demo.factory.Factory;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import java.util.Scanner;

public class StartProgram {

    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = Factory.getUserServiceInstance();
    private final UserMenu userMenu = new UserMenu();
    private final BillMenu billmenu = new BillMenu();
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public void startApp() {
        int numberOfChoice;
        do {
            System.out.println("MENU");
            System.out.println("1. Registration");
            System.out.println("2. Entrance to the cabinet");
            System.out.println("3. Remove user");
            System.out.println("0. EXIT");
            numberOfChoice = in.nextInt();
            if (numberOfChoice == 1) {
                setRegistration();
            }
            if (numberOfChoice == 2) {
                setWorkInCabinet();
            }
            if (numberOfChoice == 3) {
                userMenu.removeUser();
            }
            if (numberOfChoice != 1 && numberOfChoice != 0 && numberOfChoice != 2 && numberOfChoice != 3) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (numberOfChoice != 0);
    }

    private void setRegistration() {
        String name;
        String login;
        System.out.println("Input name of user");
        name = in.next();
        System.out.println("Enter User login");
        login = in.next();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            userService.addUser(name,login);
            System.out.println("The User was Added");
        } else {
            System.out.println("The user exists. Choose another login");
        }
    }

    private void setWorkInCabinet() {
        String login = userMenu.getLogin();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            System.out.println("User not found. Please go to Registration");
        } else {
            billmenu.enterBillMenu(user);
        }
    }
}

