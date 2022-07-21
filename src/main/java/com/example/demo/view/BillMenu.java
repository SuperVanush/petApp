package com.example.demo.view;

import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.impl.BillService;

import java.util.List;
import java.util.Scanner;

public class BillMenu {

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public final Scanner in = new Scanner(System.in);
    private final BillService billService = Factory.getBillServiceInstance();

    public void enterBillMenu(User lastUser) {
        int billChoice;
        System.out.println("Hello   " + lastUser.getName());
        do {
            System.out.println("1. Add bill ");
            System.out.println("2. Print Bills");
            System.out.println("3. Balance Transaction");
            System.out.println(PRINT_MAIN_MENU);
            billChoice = in.nextInt();
            if (billChoice == 1) {
                enterBill(lastUser);
            }
            if (billChoice == 2) {
                List<Bill> billList = billService.findBillsByUser(lastUser);
                System.out.println(billList);
            }
            if (billChoice == 3) {
                balanceTransaction(lastUser);
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

    private void balanceTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);
        System.out.println(billList);
        System.out.println("Choice the bill for transaction");
        System.out.println("Enter bill id");
        int billId = in.nextInt();
        int choiceTransaction;
        do {
            System.out.println("Choice transaction type");
            System.out.println("1. Increase Balance");
            System.out.println("2. Reduce Balance");
            System.out.println(PRINT_MAIN_MENU);
            choiceTransaction = in.nextInt();
            if (choiceTransaction == 1) {
                System.out.println("Enter digit");
                int sumDigit = in.nextInt();
                Bill bill = billService.sumBalanceTransaction(billId, sumDigit);
                System.out.println(bill);
            }
            if (choiceTransaction == 2) {
                System.out.println("Enter digit");
                int reduceDigit = in.nextInt();
                Bill bill = billService.reduceBalance(billId, reduceDigit);
                if (bill == null) {
                    System.out.println("Bill balance is minus. Reduce digit");
                } else {
                    System.out.println(bill);
                }
                if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2) {
                    System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                }
            }
        }
        while (choiceTransaction != 0);
    }
}