package com.example.demo.view;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.BillService;
import com.example.demo.service.SortService;
import com.example.demo.service.UserService;

import java.util.List;
import java.util.Scanner;

public class StartProgram {
    public static final Scanner in = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final SortService sortService = new SortService();
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
                String login = getLogin();
                User findUser = getFindedUser(login);
               setWorkInCabinet(login, findUser);
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
        if (userService.getUserList().isEmpty()) {
            userService.addUser(name, login);
            System.out.println("The User was Added");
        } else {
            User findUser = userService.findUserByElement(login);
            String findUserLogin = findUser.getLogin();
            if (!login.equals(findUserLogin)) {
                userService.addUser(name, login);
                System.out.println("The User was Added");
            }
            if (login.equals(findUserLogin)) {
                System.out.println("The user exists. Choose another name");
            }
        }
    }

     public void setWorkInCabinet(String login, User findUser) {
        do {
            if (login.equals("0")) {
                break;
            }
            if (findUser == null) {
                System.out.println("User not found. Please go to Registration");
                break;
            } else {
                String findUserLogin = findUser.getLogin();
                if (login.equals(findUserLogin)) {
                    enterBill(findUser);
                    break;
                }
            }
        } while (!login.equals("0"));
    }

    public void enterBill(User findUser) {
        int billChoice;
        do {
            System.out.println("1. Add bill ");
            System.out.println(PRINT_MAIN_MENU);
            billChoice = in.nextInt();
            if (billChoice == 1) {
                System.out.println("Input name of bill");
                String billName = in.next();
                System.out.println("Input bill balance");
                int billBalance = in.nextInt();
                billService.addBill(billName, billBalance, findUser);
                List<Bill> bills = billService.findBillsByUser(findUser);
                userService.rewriteUser(bills, findUser);
            }
            if (billChoice == 0){
                break;
            }
            if (billChoice != 1 && billChoice != 0) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (billChoice != 0);
            }

    public String getLogin() {
        String login;
        System.out.println("Enter User login");
        System.out.println(PRINT_MAIN_MENU);
        login = in.next();
        return login;
    }

    public User getFindedUser(String login) {
        User findUser = userService.findUserByElement(login);
        return findUser;
    }
}