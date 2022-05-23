package com.example.demo.view;

import com.example.demo.dao.UserStorage;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.UserService;

import java.util.Scanner;

public class StartProgram {
    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final BillService billService = new BillService();
    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public void startApp() {
        int numberOfChoice;
        do {
            System.out.println("MENU");
            System.out.println("1. Registration");
            System.out.println("2. Entrance to the cabinet");
            System.out.println("0. EXIT");
            numberOfChoice = in.nextInt();
            if (numberOfChoice == 1) {
                setRegistration();
            }
            if (numberOfChoice == 2) {
                setWorkInCabinet();
            }
        }
        while (numberOfChoice != 0);
    }

    public void setRegistration() {
        String name;
        String login;
        System.out.println("Input name of user");
        name = in.next();
        System.out.println("Enter User login");
        login = in.next();
        User findUser = userService.findUserByLogin(login);
        if (findUser == null) {
            userService.addUser(name, login);
            System.out.println("The User was Added");
        } else {
            System.out.println("The user exists. Choose another name");
        }
    }

    public String getLogin() {
        String login;
        System.out.println("Enter User login");
        login = in.next();
        return login;
    }

    public User setWorkInCabinet() {
        String login = getLogin();
        User lastUser = userService.findUserByLogin(login);
        if (lastUser == null) {
            System.out.println("User not found. Please go to Registration");
        } else {
            enterBillMenu(lastUser);
        }
        return lastUser;
    }

    public void enterBillMenu(User lastUser) {
        int billChoice;
        do {
            System.out.println("1. Add bill ");
            System.out.println(PRINT_MAIN_MENU);
            billChoice = in.nextInt();
            if (billChoice == 0) {
                break;
            }
            if (billChoice == 1) {
                enterBill(lastUser);
            }
            if (billChoice != 1 && billChoice != 0) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (billChoice != 0);
    }

    public void enterBill(User lastUser) {
        System.out.println("Input name of bill");
        String billName = in.next();
        System.out.println("Input bill balance");
        int billBalance = in.nextInt();
        billService.addBill(billName, billBalance, lastUser);
    }
}