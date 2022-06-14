package com.example.demo.view;

import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.UserService;

import java.util.List;
import java.util.Scanner;

public class StartProgram {

    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = Factory.getUserServiceInstance();
    private final BillService billService = Factory.getBillServiceInstance();
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

    private void setRegistration() {
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

    private void setWorkInCabinet() {
        String login = getLogin();
        User user = userService.findUserByLogin(login);
        if (user == null) {
            System.out.println("User not found. Please go to Registration");
        } else {
            enterBillMenu(user);
        }
    }

    private String getLogin() {
        String login;
        System.out.println("Enter User login");
        login = in.next();
        return login;
    }

    private void enterBillMenu(User lastUser) {
        int billChoice;
        do {
            System.out.println("Hello   " + lastUser.getName());
            System.out.println("1. Add bill ");
            System.out.println("2. Print User");
            System.out.println("3. Print Bills");
            System.out.println(PRINT_MAIN_MENU);
            billChoice = in.nextInt();
            if (billChoice == 1) {
                enterBill(lastUser);
            }
            if (billChoice == 2) {
                String login = lastUser.getLogin();
                User user = userService.findUserByLogin(login);
                System.out.println(user);
            }
            if (billChoice == 3) {
                List<Bill> billList = billService.findBillsByUser(lastUser);
                System.out.println(billList);
            }
            if (billChoice != 1 && billChoice != 0 && billChoice != 2 && billChoice != 3) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (billChoice != 0);
    }

    private void enterBill(User user) {
        System.out.println("Input name of bill");
        String billName = in.next();
        System.out.println("Input bill balance");
        int billBalance = in.nextInt();
        billService.addBill(billName, billBalance, user);
    }
}