package com.example.demo.view;

import com.example.demo.dao.UserStorage;
import com.example.demo.model.User;
import com.example.demo.service.SortService;
import com.example.demo.service.UserService;
import com.sun.source.tree.IfTree;

import java.util.Scanner;

public class StartProgram {
    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SortService sortService = new SortService();


    public void startApp() {
        int numberOfChoice;
        String choiceOfPointMenu;
        System.out.println("MENU");
        System.out.println("Select action");
        System.out.println("1. Add User");
        System.out.println("2. Sort users");
        System.out.println("3. Delete user");
        System.out.println("4. Print users");
        System.out.println("5. Other action");
        System.out.println("Make your choice");
        numberOfChoice = in.nextInt();
        if (numberOfChoice == 1) {
            do {
                setChoiceAddUser();
                System.out.println("Do you want to continue ?   y/n");
                choiceOfPointMenu = in.next();
                startApp();
            }
            while (choiceOfPointMenu.equals("y"));
        }
        if (numberOfChoice == 2) {
            do {
                sortMenu();
                System.out.println("Do you want to continue ?   y/n");
                choiceOfPointMenu = in.next();
                startApp();
            }
            while (choiceOfPointMenu.equals("y"));
        }
        if (numberOfChoice == 3) {
            do {
                removeUserById();
                System.out.println("Do you want to continue ?   y/n");
                choiceOfPointMenu = in.next();
                startApp();
            }
            while (choiceOfPointMenu.equals("y"));
        }
        if (numberOfChoice == 4){
            printUserList();
            System.out.println("Do you want to continue ?   y/n");
            choiceOfPointMenu = in.next();
            startApp();
        }
        if (numberOfChoice ==5 )
        {
            System.out.println("The action not defined yet");
            System.out.println("Do you want to continue ?   y/n");
            choiceOfPointMenu = in.next();
            startApp();
        }
        else {
            System.out.println("Wrong choice. Try agane ");
            startApp();
        }

    }

    public void setChoiceAddUser() {
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
        startApp();
    }

      public void sortMenu() {
        int choiceNumberSort;
        String continueChoice;
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
            startApp();
        }
        while (continueChoice.equals("y"));
    }
    public void removeUserById() {
        String continueChoice;
        do {
            System.out.println("Input ID of user to delete");
            int id = in.nextInt();
            userService.removeUserById(id);
            System.out.println("Do you want to continue ?   y/n");
            continueChoice = in.next();
        }
        while (continueChoice.equals("y"));
        startApp();
    }

    public void printUserList (){
        userService.printUserList();
    }
    }
