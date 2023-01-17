package com.example.demo.view;

import com.example.demo.exception.MyException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.ServiceBill;
import com.example.demo.service.ServiceUser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class BillMenu {
    private ServiceBill billService;
    private ServiceUser userService;

    public BillMenu(ServiceBill billService, ServiceUser userService) {
        this.billService = billService;
        this.userService = userService;
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
        int choiceTransaction;
        do {
            System.out.println("Choice transaction type");
            System.out.println("1. Increase Balance");
            System.out.println("2. Reduce Balance");
            System.out.println("3. Transaction between Bills");
            System.out.println("4. Transaction between UsersBills");
            System.out.println("5. Transaction to User's Random Bill");
            System.out.println(PRINT_MAIN_MENU);
            choiceTransaction = in.nextInt();
            if (choiceTransaction == 1) {
                System.out.println("Choice the bill for transaction");
                List<Bill> billList = billService.findBillsByUser(lastUser);
                for (Bill billInList : billList) {
                    int countNumberBill = billList.indexOf(billInList) + 1;
                    System.out.println(countNumberBill + ". name of bill:     " + billInList.getName());
                }
                System.out.println("Choice bill");
                int billIndex = in.nextInt() - 1;
                int billId = billList.get(billIndex).getId();
                System.out.println("Enter digit");
                int sumDigit = in.nextInt();
                Bill bill = billService.sumBalanceTransaction(billId, sumDigit);
                System.out.println(bill);
            }
            if (choiceTransaction == 2) {
                System.out.println("Choice the bill for transaction");
                List<Bill> billList = billService.findBillsByUser(lastUser);
                for (Bill billInList : billList) {
                    int countNumberBill = billList.indexOf(billInList) + 1;
                    System.out.println(countNumberBill + ". name of bill:     " + billInList.getName());
                }
                System.out.println("Choice bill");
                int billIndex = in.nextInt() - 1;
                int billId = billList.get(billIndex).getId();
                System.out.println("Enter digit");
                int reduceDigit = in.nextInt();
                try {
                    Bill bill = billService.reduceBalance(billId, reduceDigit);
                    System.out.println(bill);
                } catch (MyException e) {
                    System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
                }
            }
            if (choiceTransaction == 3) {
                betweenBillTransaction(lastUser);
            }
            if (choiceTransaction == 4) {
                betweenUsersTransaction(lastUser);
            }
            if (choiceTransaction == 5) {
                transactionToRandomBill(lastUser);
            }
            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2
                    && choiceTransaction != 3 && choiceTransaction != 4 && choiceTransaction != 5) {
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
        List<Bill> billListFromUser = billService.findBillsByUser(lastUser);
        for (Bill billInList : billListFromUser) {
            int countNumberFromBill = billListFromUser.indexOf(billInList) + 1;
            System.out.println(countNumberFromBill + ". name of bill:    " + billInList.getName() +
                    "balance =  " + billInList.getBalance());
        }
        System.out.println("Enter Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        System.out.println("Enter login User for add money");
        String loginToUser = in.next();
        User userToTransaction = userService.findUserByLogin(loginToUser);
        if (userToTransaction == null) {
            System.out.println("User not found. Please enter other User");
        } else {
            List<Bill> billListToUser = billService.findBillsByUser(userToTransaction);
            for (Bill billInListToUser : billListToUser) {
                int countNumberToBill = billListToUser.indexOf(billInListToUser) + 1;
                System.out.println(countNumberToBill + ". name of bill:    " + billInListToUser.getName()
                        + "balance =  " + billInListToUser.getBalance());
            }
            System.out.println("Enter Bill of ToUser for add money");
            int toToBillIndex = in.nextInt() - 1;
            System.out.println("Enter the transaction summa");
            int transactionSumma = in.nextInt();
            int idFromBill = billListFromUser.get(fromBillIndex).getId();
            int idToBill = billListToUser.get(toToBillIndex).getId();
            try {
                billService.transactionBetweenUsers(idFromBill, idToBill, transactionSumma);
                List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
                for (Bill fromBillInList : lastFromBillList) {
                    System.out.println(lastUser.getName() + ". name of bill:    "
                            + fromBillInList.getName() + "balance =  "
                            + fromBillInList.getBalance());
                }
                List<Bill> lastToBillList = billService.findBillsByUser(userToTransaction);
                for (Bill toBillInList : lastToBillList) {
                    System.out.println(userToTransaction.getName() + ". name of bill:    "
                            + toBillInList.getName() + "balance =  " + toBillInList.getBalance());
                }
            } catch (MyException e) {
                System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
            }
        }
    }

    private void transactionToRandomBill(User lastUser) {
        List<Bill> billListFromUser = billService.findBillsByUser(lastUser);
        for (Bill billInList : billListFromUser) {
            int countNumberFromBill = billListFromUser.indexOf(billInList) + 1;
            System.out.println(countNumberFromBill + ". name of bill:    " + billInList.getName() +
                    "balance =  " + billInList.getBalance());
        }
        System.out.println("Enter Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        System.out.println("Enter login User for add money");
        String loginToUser = in.next();
        User userToTransaction = userService.findUserByLogin(loginToUser);
        if (userToTransaction == null) {
            System.out.println("User not found. Please enter other User");
        } else {
            System.out.println("Enter the transaction summa");
            int transactionSumma = in.nextInt();
            int idFromBill = billListFromUser.get(fromBillIndex).getId();
            try {
                billService.transactionToRandomBill(idFromBill, userToTransaction, transactionSumma);
                List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
                for (Bill fromBillInList : lastFromBillList) {
                    System.out.println(lastUser.getName() + ". name of bill:    "
                            + fromBillInList.getName() + "balance =  "
                            + fromBillInList.getBalance());
                }
                List<Bill> lastToBillList = billService.findBillsByUser(userToTransaction);
                for (Bill toBillInList : lastToBillList) {
                    System.out.println(userToTransaction.getName() + ". name of bill:    "
                            + toBillInList.getName() + "balance =  " + toBillInList.getBalance());
                }
            } catch (MyException e) {
                System.out.println("fufufu, TRY AGAIN YOUR BALANCE IS MINUS");
            }
        }
    }
}