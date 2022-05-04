package com.example.demo.view;

import com.example.demo.service.SortService;
import com.example.demo.service.UserService;

import java.util.Scanner;

public class StartProgram {
    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SortService sortService = new SortService();
    private static final String PRINT_MAIL_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public void startApp() {
        int numberOfChoice;
        do {
            System.out.println("MENU");
            System.out.println("Select action");
            System.out.println("1. Add User");
            System.out.println("2. Sort users");
            System.out.println("3. Delete user");
            System.out.println("4. Print users");
            System.out.println("5. Other action");
            System.out.println("0. End program");
            System.out.println("Make your choice");
            numberOfChoice = in.nextInt();
            if (numberOfChoice == 1) {
                setChoiceAddUser();
            }
            if (numberOfChoice == 2) {
                sortMenu();
            }
            if (numberOfChoice == 3) {
                removeUserById();
            }
            if (numberOfChoice == 4) {
                getUserList();
            }
            if (numberOfChoice == 5) {
                System.out.println("The action not defined yet");
            }
        }
        while (numberOfChoice != 0);
    }

    public void setChoiceAddUser() {
        int numberOfChoice;
        do {
            System.out.println("1. ADD USER");
            System.out.println(PRINT_MAIL_MENU);
            numberOfChoice = in.nextInt();
            if (numberOfChoice == 1) {
                String name;
                System.out.println("Input name of user");
                name = in.next();
                userService.addUser(name);
                System.out.println("The User was Added");
                int billChoice;
                do {
                    System.out.println("1. Add bill ");
                    System.out.println(PRINT_MAIL_MENU);
                    billChoice = in.nextInt();
                    if (billChoice == 1) {
                        System.out.println("Input name of bill");
                        String billName = in.next();
                        System.out.println("Input bill balance");
                        int billBalance = in.nextInt();
                        int idLastUser = 0;
                        userService.rewriteUser(billName, billBalance);
                    }
                    if  (billChoice != 1 && billChoice != 0) {
                        System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                    }
                }
                while (billChoice != 0);
                if (numberOfChoice != 1 && numberOfChoice != 0) {
                    System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                }
            }
        }
        while (numberOfChoice != 0);
    }

    public void sortMenu() {
        int choiceNumberSort;
        do {
            System.out.println("Add sort method");
            System.out.println("1. Sort by name of users");
            System.out.println("2. Sort by ID of users");
            System.out.println(PRINT_MAIL_MENU);
            choiceNumberSort = in.nextInt();
            if (choiceNumberSort == 1) {
                System.out.println(sortService.startSortUsersByName());
            }
            if (choiceNumberSort == 2) {
                System.out.println(sortService.startSortUsersByNumber());
            }
            if (choiceNumberSort != 1 && choiceNumberSort != 2 && choiceNumberSort != 0) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (choiceNumberSort != 0);
    }

    public void removeUserById() {
        int choiceNumberSort;
        do {
            System.out.println("1. Remove User");
            System.out.println(PRINT_MAIL_MENU);
            choiceNumberSort = in.nextInt();
            if (choiceNumberSort == 1) {
                System.out.println("Input ID of user to delete");
                int id = in.nextInt();
                userService.removeUserById(id);
                System.out.println("The User was removed");
            }
            if (choiceNumberSort != 1 && choiceNumberSort != 0) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (choiceNumberSort != 0);
    }

    public void getUserList() {
        System.out.println(userService.getUserList());
    }
}

