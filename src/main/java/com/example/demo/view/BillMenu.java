package com.example.demo.view;

import com.example.demo.exception.MyException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceBill;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class BillMenu {
    private ServiceBill billService;

    public BillMenu(ServiceBill billService) {
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
            System.out.println("4. Transaction between Bills");
            System.out.println("5. Transaction between Users");
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
            if (billChoice == 4) {
                betweenBillTransaction(lastUser);
            }
            if (billChoice == 5) {
                betweenUsersTransaction(lastUser);
            }
            if (billChoice != 1 && billChoice != 0 && billChoice != 2 && billChoice != 3
                    && billChoice != 4 && billChoice != 5) {
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
            System.out.println(countNumberBill + ". name of bill:     " + billInList.getName());
        }
        System.out.println("Choice bill");
        int billIndex = in.nextInt() - 1;
        int billId = billList.get(billIndex).getId();
        int choiceTransaction;
        do {
            System.out.println("Choice transaction type");
            System.out.println("1. Increase Balance");
            System.out.println("2. Reduce Balance");
            System.out.println("3. Transaction between Bills");
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
                    System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
                }
            }
            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        }
        while (choiceTransaction != 0);
    }

    private void betweenBillTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill:    " + billInList.getName() + "balance =  "
                    + billInList.getBalance());
        }
        System.out.println("Enter ID Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        System.out.println("Enter ID Bill for add money");
        int toBillIndex = in.nextInt() - 1;
        System.out.println("Enter the transaction summa");
        int transactionSumma = in.nextInt();
        int idFromBill = billList.get(fromBillIndex).getId();
        int idToBill = billList.get(toBillIndex).getId();
        try {
            billService.transactionBetweenBills(idFromBill, idToBill, transactionSumma);
            List<Bill> transactionBillList = billService.findBillsByUser(lastUser);
            for (Bill billInList : transactionBillList) {
                int countNumberBill = billList.indexOf(billInList) + 1;
                System.out.println(countNumberBill + ". name of bill:    " + billInList.getName() + "balance =  "
                        + billInList.getBalance());
            }
        } catch (MyException e) {
            System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
        }
    }

    private void betweenUsersTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill:    " + billInList.getName() + "balance =  "
                    + billInList.getBalance());
        }
        System.out.println("Enter ID Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        System.out.println("Enter ID User for add money");
        int idToUser = in.nextInt();
        System.out.println("Enter ID Bill of ToUser for add money");
        int idToBill = in.nextInt();
        System.out.println("Enter the transaction summa");
        int transactionSumma = in.nextInt();
        int idFromBill = billList.get(fromBillIndex).getId();
        try {
            billService.transactionBetweenUsers(lastUser, idToUser, idFromBill,
                    idToBill, transactionSumma);
            List<Bill> transactionBillList = billService.findBillsByUser(lastUser);
            for (Bill billInList : transactionBillList) {
                int countNumberBill = billList.indexOf(billInList) + 1;
                System.out.println(countNumberBill + ". name of bill:    " + billInList.getName() + "balance =  "
                        + billInList.getBalance());
            }
        } catch (MyException e) {
            System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
        }
    }
}