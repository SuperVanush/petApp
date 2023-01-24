package com.example.demo.view;

import com.example.demo.exception.MyException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class TransactionMenu {
    private BillService billService;
    private UserService userService;

    public TransactionMenu(BillService billService, UserService userService) {
        this.billService = billService;
        this.userService = userService;
    }

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    public final Scanner in = new Scanner(System.in);

    public void balanceTransaction(User lastUser) {
        int choiceTransaction;
        do {
            System.out.println("Choice transaction type");
            System.out.println("1. Increase Balance");
            System.out.println("2. Reduce Balance");
            System.out.println("3. Transaction between Bills");
            System.out.println("4. Transaction to other User");
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
                    System.out.println(e.getMessage());
                }
            }
            if (choiceTransaction == 3) {
                betweenBillTransaction(lastUser);
            }
            if (choiceTransaction == 4) {
                transactionToOtherUser(lastUser);
            }

            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2 && choiceTransaction != 3 && choiceTransaction != 4) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        } while (choiceTransaction != 0);
    }

    public void transactionToOtherUser(User lastUser) {
        int choiceTransaction;
        do {
            System.out.println("1. Transaction to other User's Bill");
            System.out.println("2. Transaction to other User's Random Bill");
            System.out.println(PRINT_MAIN_MENU);
            choiceTransaction = in.nextInt();
            if (choiceTransaction == 1) {
                betweenUsersTransaction(lastUser);
            }
            if (choiceTransaction == 2) {
                transactionToRandomBill(lastUser);
            }
            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        } while (choiceTransaction != 0);
    }

    private void betweenBillTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill:   " + billInList.getName() + ".   balance =  " + billInList.getBalance());
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
            billService.reduceBalance(idFromBill, transactionSumma);
            billService.sumBalanceTransaction(idToBill, transactionSumma);
            List<Bill> transactionBillList = billService.findBillsByUser(lastUser);
            for (Bill billInList : transactionBillList) {
                int countNumberBill = transactionBillList.indexOf(billInList) + 1;
                System.out.println(countNumberBill + ". name of bill:   " + billInList.getName() + ".   balance =  " + billInList.getBalance());
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void betweenUsersTransaction(User lastUser) {
        int idFromBill = choiceFromBillId(lastUser);
        User userToTransaction = choiceToUser();
        try {
            List<Bill> billListToUser = billService.findBillsByUser(userToTransaction);
            for (Bill billInListToUser : billListToUser) {
                int countNumberToBill = billListToUser.indexOf(billInListToUser) + 1;
                System.out.println(countNumberToBill + ". name of bill:   " + billInListToUser.getName() + ".   balance =  " + billInListToUser.getBalance());
            }
            System.out.println("Enter Bill of ToUser for add money");
            int toToBillIndex = in.nextInt() - 1;
            System.out.println("Enter the transaction summa");
            int transactionSumma = in.nextInt();
            int idToBill = billListToUser.get(toToBillIndex).getId();
            try {
                billService.reduceBalance(idFromBill, transactionSumma);
                billService.sumBalanceTransaction(idToBill, transactionSumma);
                List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
                for (Bill fromBillInList : lastFromBillList) {
                    System.out.println(lastUser.getName() + ". name of bill:   " + fromBillInList.getName() + ".   balance =  " + fromBillInList.getBalance());
                }
                List<Bill> lastToBillList = billService.findBillsByUser(userToTransaction);
                for (Bill toBillInList : lastToBillList) {
                    System.out.println(userToTransaction.getName() + ". name of bill:    " + toBillInList.getName() + "balance =  " + toBillInList.getBalance());
                }
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private void transactionToRandomBill(User lastUser) {
        try {
            int idFromBill = choiceFromBillId(lastUser);
            User userToTransaction = choiceToUser();
            billService.findBillsByUser(userToTransaction);
            System.out.println("Enter the transaction summa");
            int transactionSumma = in.nextInt();
            try {
                billService.transactionToRandomBill(idFromBill, userToTransaction, transactionSumma);
                List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
                for (Bill fromBillInList : lastFromBillList) {
                    System.out.println(lastUser.getName() + ". name of bill:   " + fromBillInList.getName() + ".   balance =  " + fromBillInList.getBalance());
                }
                List<Bill> lastToBillList = billService.findBillsByUser(userToTransaction);
                for (Bill toBillInList : lastToBillList) {
                    System.out.println(userToTransaction.getName() + ". name of bill:   " + toBillInList.getName() + ".   balance =  " + toBillInList.getBalance());
                }
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    private int choiceFromBillId(User lastUser) throws MyException {
        List<Bill> billListFromUser = billService.findBillsByUser(lastUser);
        if (billListFromUser.isEmpty()) {
            throw new MyException("This user has not bills");
        } else {
            for (Bill billInList : billListFromUser) {
                int countNumberFromBill = billListFromUser.indexOf(billInList) + 1;
                System.out.println(countNumberFromBill + ". name of bill:   " + billInList.getName() + ".   balance =  " + billInList.getBalance());
            }
            System.out.println("Enter Bill for write off money");
            int fromBillIndex = in.nextInt() - 1;
            int idFromBill = billListFromUser.get(fromBillIndex).getId();
            return idFromBill;
        }
    }

    private User choiceToUser() {
        System.out.println("Enter login User for add money");
        String loginToUser = in.next();
        User userToTransaction = null;
        try {
            userToTransaction = userService.findUserByLogin(loginToUser);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return userToTransaction;
    }
}