package com.example.demo.view;

import com.example.demo.dao.UserStorage;
import com.example.demo.service.SortService;
import com.example.demo.service.UserService;

import java.util.Scanner;

public class StartProgram {

    private final SortService sortService = new SortService();
    private final UserService userService = new UserService();
    public static final Scanner in = new Scanner(System.in);

    public void startApp() {
        String choiceAddUser;
        String name;
        do {
            System.out.println("Input name of user");
            name = in.next();
            userService.makeAddUser(name);
            System.out.println("Do you want add next user? y/n");
            choiceAddUser = in.next();
        }
        while (choiceAddUser.equals("y"));
        sortMenu();
    }

    public void sortMenu() {
        int choiceNumberSort;
        String continueChoice;
        System.out.println("MENU");
        System.out.println("1. Sort users");
        System.out.println("2. Delete user");
        System.out.println("3. Other action");
        System.out.println("Make your choice");
        int actionChoice = in.nextInt();
        if (actionChoice == 1) {
            do {
                System.out.println("Add sort metod");
                System.out.println("1. Sort by name of users");
                System.out.println("2. Sort by ID of users");
                choiceNumberSort = in.nextInt();
                if (choiceNumberSort == 1) {
                    System.out.println(sortService.startSortUsersByName());
                }
                if (choiceNumberSort == 2) {
                    System.out.println(sortService.startSortUsersByNumber());
                }
                System.out.println("Do you want to continue ?   y/n");
                continueChoice = in.next();
            }
            while (continueChoice.equals("y"));
        }
        if (actionChoice ==2) {
            do {
                System.out.println("Input ID of user to delete");
                int id = in.nextInt();
                userService.removeUserById(id);
            System.out.println("Do you want to continue ?   y/n");
            continueChoice = in.next();}
            while (continueChoice.equals("y"));
        }
        else {
            System.out.println("other choice is wrong");
        }
    }
}