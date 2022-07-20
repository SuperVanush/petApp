package com.example.demo.view;

import com.example.demo.factory.Factory;

import java.util.Scanner;

public class StartProgram {

    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public final Scanner in = new Scanner(System.in);
    private final UserMenu userMenu = Factory.getUserMenuInstance();

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
                userMenu.setRegistration();
            }
            if (numberOfChoice == 2) {
                userMenu.setWorkInCabinet();
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
}