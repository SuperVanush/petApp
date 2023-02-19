package com.example.demo.view;

import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class UserMenu {

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    private BillMenu billMenu;
    private UserService userService;

    public UserMenu(BillMenu billMenu, UserService userService) {
        this.billMenu = billMenu;
        this.userService = userService;
    }

    private final Scanner in = new Scanner(System.in);

    public void setRegistration() {
        String name;
        String login;
        String password;
        System.out.println("Input name of user");
        name = in.next();
        System.out.println("Enter User login");
        login = in.next();
        try {
            userService.findUserByLogin(login);
            System.out.println("The user exists. Choose another login");
        } catch (MyExceptionUser ex) {
            System.out.println("Enter User password");
            password = in.next();
            userService.addUser(name, login, password);
            System.out.println("The User was Added");
        }
    }

    public void setWorkInCabinet() {
        System.out.println("Enter User login");
        String login = in.next();
        try {
            User userByLogin = userService.findUserByLogin(login);
            System.out.println("Enter User password");
            String password = in.next();
            if (userByLogin.getPassword().equals(password)) {
                int userMenuChoice;
                do {
                    System.out.println("1. Print User");
                    System.out.println("2. Go to Bills menu");
                    System.out.println(PRINT_MAIN_MENU);
                    userMenuChoice = in.nextInt();
                    if (userMenuChoice == 1) {
                        System.out.println(userByLogin);
                    }
                    if (userMenuChoice == 2) {
                        billMenu.enterBillMenu(userByLogin);
                    }
                    if (userMenuChoice != 1 && userMenuChoice != 0 && userMenuChoice != 2) {
                        System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                    }
                }
                while (userMenuChoice != 0);
            } else {
                System.out.println("Wrong password");
            }
        } catch (
                MyExceptionUser ex) {
            System.out.println(ex.getMessage());
        }

    }


    public void removeUser() {
        System.out.println("Enter login of user to remove");
        String loginRemoveUser = in.next();
        userService.removeUser(loginRemoveUser);
        System.out.println("User was removed");
    }
}