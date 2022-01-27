package com.example.demo.view;

import com.example.demo.service.SortService;
import com.example.demo.service.UserService;
import com.sun.source.tree.IfTree;

import java.util.Scanner;

public class StartProgram {

    private static final String ONE = "1. %s";
    private static final String TWO = "2. %s";
    private static final String THREE = "3. %s";
    private static final String FOUR = "4. %s";
    private static final String FIVE = "5. %s";
    private static final String ZERO = "0. %s";
    private static final String RETURN_TO_MAIN_MENU = "Return to main menu";
    private static final String ERROR_MESSAGE_MENU = "ERROR";

    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SortService sortService = new SortService();

    public void startApp() {
        int choiceNumber;
        do {
            System.out.println("MENU");
            System.out.println("Select action");
            System.out.println(String.format(ONE, "Add User"));
            System.out.println(String.format(TWO, "Sort users and print"));
            System.out.println(String.format(THREE, "Delete User"));
            System.out.println(String.format(FOUR, "Print Users"));
            System.out.println(String.format(ZERO, "End Program"));
            choiceNumber = in.nextInt();
            if (choiceNumber == 1) {
                addUserChoice();
            }
            if (choiceNumber == 2) {
                sortUsersChoice();
            }
            if (choiceNumber == 3) {
                removeUser();
            }
            if (choiceNumber == 4) {
                printUserList();
            }
            if (choiceNumber == 6) {
                System.out.println("The action not defined yet");
            }
        }
        while (choiceNumber != 0);
    }

    public void addUserChoice() {
        int choiceNumber;
        do {
            System.out.println(String.format(ONE, "Add User"));
            System.out.println(String.format(TWO, "Add several Users"));
            System.out.println(String.format(ZERO, RETURN_TO_MAIN_MENU));
            choiceNumber = in.nextInt();
            if (choiceNumber == 1) {
                String name;
                System.out.println("Input name of user");
                name = in.next();
                userService.addUser(name);
                System.out.println("The User was Added");
                do {
                    System.out.println(String.format(ONE, "Add Bill"));
                    System.out.println(String.format(ZERO, RETURN_TO_MAIN_MENU));
                    choiceNumber = in.nextInt();
                    if (choiceNumber == 1) {
                        String billName;
                        int billNumber;
                        System.out.println("Input name of bill");
                        billName = in.next();
                                           }
                }
                while (choiceNumber!=0);
                }

            if (choiceNumber == 2) {
                String severalNames;
                System.out.println("Input names of users");
                severalNames = in.next();
                userService.addSeveralUsers(severalNames);
                System.out.println("The Users were Added");
            }
            if (choiceNumber != 1
                    && choiceNumber != 2
                    && choiceNumber != 0) {
                System.err.println(ERROR_MESSAGE_MENU);
            }
        }
        while (choiceNumber != 0);
    }

    public void sortUsersChoice() {
        int choiceNumber;
        do {
            System.out.println("Add sort method");
            System.out.println(String.format(ONE, "Sort by name of users"));
            System.out.println(String.format(TWO, "Sort by ID of users"));
            System.out.println(String.format(ZERO, RETURN_TO_MAIN_MENU));
            choiceNumber = in.nextInt();
            if (choiceNumber == 1) {
                System.out.println(sortService.startSortUsersByName());
            }
            if (choiceNumber == 2) {
                System.out.println(sortService.startSortUsersByNumber());
            }
            if (choiceNumber != 1
                    && choiceNumber != 2
                    && choiceNumber != 0) {
                System.err.println(ERROR_MESSAGE_MENU);
            }
        }
        while (choiceNumber != 0);
    }

    public void removeUser() {
        int choiceNumber;
        do {
            System.out.println(String.format(ONE, "Remove User"));
            System.out.println(String.format(ZERO, RETURN_TO_MAIN_MENU));
            choiceNumber = in.nextInt();
            if (choiceNumber == 1) {
                System.out.println("Input ID of user to delete");
                int id = in.nextInt();
                userService.removeUserById(id);
                System.out.println("The User was removed");
            }
            if (choiceNumber != 1
                    && choiceNumber != 0) {
                System.err.println(ERROR_MESSAGE_MENU);
            }
        }
        while (choiceNumber != 0);
    }

    public void printUserList() {
        System.out.println(userService.getUserList());
    }
}

