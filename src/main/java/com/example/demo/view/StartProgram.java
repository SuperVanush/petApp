package com.example.demo.view;

import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class StartProgram {
    private UserMenu userMenu;

    public StartProgram(UserMenu userMenu) {
        this.userMenu = userMenu;
    }

    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";
    public final Scanner in = new Scanner(System.in);

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
            if (numberOfChoice != 1 && numberOfChoice != 0
                    && numberOfChoice != 2 && numberOfChoice != 3) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (numberOfChoice != 0);
    }
}