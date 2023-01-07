package com.example.demo.view;

import com.example.demo.exception.MyException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.impl.BillService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class BillMenu {
    private BillService billService;

    public BillMenu(BillService billService) {
        this.billService = billService;
    }

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public final Scanner in = new Scanner(System.in);


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
        System.out.println("Choice the bill for transaction");
        List<Bill> billList = billService.findBillsByUser(lastUser);
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill" + billInList.getName());
        }
        System.out.println("Choice bill");
        int billIndex = in.nextInt() - 1;
        int billId = billList.get(billIndex).getId();
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
                try {
                    Bill bill = billService.reduceBalance(billId, reduceDigit);
                    System.out.println(bill);
                } catch (MyException e) {
                    System.out.println("fufufu, TRY AGANE YOUR BALANCE IS MINUS");
                }
            }
            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (choiceTransaction != 0);
    }
}