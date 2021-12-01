package com.example.demo.view;

import com.example.demo.service.SortService;
import com.example.demo.service.UserService;
import java.util.Scanner;

public class StartProgram {
    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SortService sortService = new SortService();

    public void startApp() {
        int numberOfChoice;
        String choiceOfPointMenu;
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
                printUserList();
            }
            if (numberOfChoice == 5) {
                System.out.println("The action not defined yet");
            }
            if (numberOfChoice == 0)
            {
                break;
            }
                    }
        while (numberOfChoice!=9);
    }
    public void setChoiceAddUser() {
        String choiceToBackToMainMenu;
        do {
            String name;
            System.out.println("Input name of user");
            name = in.next();
            userService.makeAddUser(name);
            System.out.println("The User was Added");
            System.out.println("Input next name?      y/n   ");
            choiceToBackToMainMenu = in.next();
                    }
        while (choiceToBackToMainMenu.equals("y"));
    }
    public void sortMenu() {
        int choiceNumberSort;
       String sortChoiceContinue;
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
           System.out.println("Doyou want to choice next sort?     y/n  ");
           sortChoiceContinue=in.next();
       }
       while (sortChoiceContinue.equals("y"));
    }
    public void removeUserById() {
        System.out.println("Input ID of user to delete");
        int id = in.nextInt();
        userService.removeUserById(id);
    }
    public void printUserList() {
        userService.printUserList();
    }
}

