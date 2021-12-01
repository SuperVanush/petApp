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
        int numberOfChoiseAddUser;
        do {
            System.out.println("1. ADD USER");
            System.out.println("2. Return to main menu");
            numberOfChoiseAddUser = in.nextInt();
                   if (numberOfChoiseAddUser == 1) {
                String name;
                System.out.println("Input name of user");
                name = in.next();
                userService.makeAddUser(name);
                System.out.println("The User was Added");
            }
                   if (numberOfChoiseAddUser==2){
               break;
                   }
                   if (numberOfChoiseAddUser!=1 && numberOfChoiseAddUser!=2)
                   {
                       System.err.println("Error");
                       numberOfChoiseAddUser =1;
                   }
        }
        while (numberOfChoiseAddUser==1);
    }
    public void sortMenu() {
        int choiceNumberSort;
           do {
           System.out.println("Add sort metod");
           System.out.println("1. Sort by name of users");
           System.out.println("2. Sort by ID of users");
           System.out.println("3. Return to main menu");
           choiceNumberSort = in.nextInt();
           if (choiceNumberSort == 1) {
               System.out.println(sortService.startSortUsersByName());
           }
           if (choiceNumberSort == 2) {
               System.out.println(sortService.startSortUsersByNumber());
           }
           if (choiceNumberSort ==3)
               {
                   break;
               }
           if (choiceNumberSort!=1&&choiceNumberSort!=2&&choiceNumberSort!=3)
           {
               System.err.println("ERROR");
               choiceNumberSort=1;
           }
       }
       while (choiceNumberSort==1 || choiceNumberSort==2 || choiceNumberSort==3);
    }
    public void removeUserById() {
        int removeMenuChoice;
        do {
            System.out.println("1. Remove User");
            System.out.println("2. Return to main menu");
            removeMenuChoice = in.nextInt();
            if (removeMenuChoice == 1) {
                System.out.println("Input ID of user to delete");
                int id = in.nextInt();
                userService.removeUserById(id);
            }
            if (removeMenuChoice==2){
                break;
            }
            if (removeMenuChoice !=1&& removeMenuChoice!=2){
                System.err.println("ERROR");
                removeMenuChoice=1;
            }
        }
        while (removeMenuChoice==1||removeMenuChoice==2);
    }
    public void printUserList() {
        userService.printUserList();
    }
}

