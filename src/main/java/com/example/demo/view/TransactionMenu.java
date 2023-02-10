package com.example.demo.view;

import com.example.demo.exception.MyExceptionBill;
import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import com.example.demo.service.impl.UserService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

@Service
public class TransactionMenu {

    private static final String PRINT_MAIN_MENU = "0. Return to main menu";
    private static final String MESSAGE_ERROR_BY_CHOICE_MENU = "ERROR";

    private BillService billService;
    private UserService userService;
    private TransferService transferService;

    public TransactionMenu(BillService billService, UserService userService, TransferService transferService) {
        this.billService = billService;
        this.userService = userService;
        this.transferService = transferService;
    }

    public final Scanner in = new Scanner(System.in);

    public void balanceTransaction(User lastUser) {
        int choiceTransaction;
        do {
            System.out.println("Choice transaction type");
            System.out.println("1. Increase Balance");
            System.out.println("2. Reduce Balance");
            System.out.println("3. Transaction between Bills");
            System.out.println("4. Transaction to other User");
            System.out.println("5. Print my transactions");
            System.out.println(PRINT_MAIN_MENU);
            choiceTransaction = in.nextInt();
            if (choiceTransaction == 1) {
                sumBalance(lastUser);
            }
            if (choiceTransaction == 2) {
                reduceBalance(lastUser);
            }
            if (choiceTransaction == 3) {
                betweenBillTransaction(lastUser);
            }
            if (choiceTransaction == 4) {
                transactionToOtherUser(lastUser);
            }
            if (choiceTransaction == 5) {
                printBillTransaction(lastUser);
            }
            if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2 && choiceTransaction != 3 && choiceTransaction != 4 && choiceTransaction != 5) {
                System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
            }
        } while (choiceTransaction != 0);
    }

    private void sumBalance(User lastUser) {
        int billId = getBillId(lastUser);
        int sumDigit = in.nextInt();
        Bill bill = billService.sumBalanceTransaction(billId, sumDigit);
        System.out.println(bill);
    }

    private void reduceBalance(User lastUser) {
        int billId = getBillId(lastUser);
        int reduceDigit = in.nextInt();
        try {
            Bill bill = billService.reduceBalance(billId, reduceDigit);
            System.out.println(bill);
        } catch (MyExceptionBill e) {
            System.out.println(e.getMessage());
        }
    }

    private int getBillId(User lastUser) {
        System.out.println("Choice the bill for transaction");
        List<Bill> billList = billService.findBillsByUser(lastUser);
        printBills(billList);
        System.out.println("Choice bill");
        int billIndex = in.nextInt() - 1;
        int billId = billList.get(billIndex).getId();
        System.out.println("Enter digit");
        return billId;
    }

    private void printBills(List<Bill> billList) {
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill:     " + billInList.getName());
        }
    }

    private void betweenBillTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);
        printBillsWithBalance(billList);

        System.out.println("Enter ID Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        System.out.println("Enter ID Bill for add money");
        int toBillIndex = in.nextInt() - 1;
        System.out.println("Enter the transaction summa");

        int transactionSumma = in.nextInt();
        int idFromBill = billList.get(fromBillIndex).getId();
        int idToBill = billList.get(toBillIndex).getId();
        User toUser = lastUser;
        try {
            billService.transactionToBill(idFromBill, idToBill, transactionSumma);
            transferService.addTransfer(lastUser, toUser, idFromBill, idToBill, transactionSumma);

            List<Bill> transactionBillList = billService.findBillsByUser(lastUser);
            printBillsWithBalance(transactionBillList);
        } catch (MyExceptionBill e) {
            System.out.println(e.getMessage());
        }
    }

    private void printBillsWithBalance(List<Bill> billList) {
        for (Bill billInList : billList) {
            int countNumberBill = billList.indexOf(billInList) + 1;
            System.out.println(countNumberBill + ". name of bill:   " + billInList.getName() + ".   balance =  " + billInList.getBalance());
        }
    }

    public void transactionToOtherUser(User lastUser) {
        try {
            User toUser = choiceToUser();
            billService.findBillsByUser(toUser);
            int choiceTransaction;
            do {
                System.out.println("1. Transaction to other User's Bill");
                System.out.println("2. Transaction to other User's Random Bill");

                System.out.println(PRINT_MAIN_MENU);
                choiceTransaction = in.nextInt();
                if (choiceTransaction == 1) {
                    betweenUsersTransaction(lastUser, toUser);
                }
                if (choiceTransaction == 2) {
                    transactionToRandomBill(lastUser, toUser);
                }
                if (choiceTransaction != 1 && choiceTransaction != 0 && choiceTransaction != 2) {
                    System.err.println(MESSAGE_ERROR_BY_CHOICE_MENU);
                }
            } while (choiceTransaction != 0);
        } catch (MyExceptionUser | MyExceptionBill e) {
            System.out.println(e.getMessage());
        }
    }

    private User choiceToUser() throws MyExceptionUser {
        System.out.println("Enter login User for add money");
        String loginToUser = in.next();
        User toUser = userService.findUserByLogin(loginToUser);
        return toUser;
    }

    private void betweenUsersTransaction(User lastUser, User toUser) {
        int idFromBill = choiceFromBillId(lastUser);
        List<Bill> billListToUser = billService.findBillsByUser(toUser);
        printBillsWithBalance(billListToUser);

        System.out.println("Enter Bill of   " + toUser.getLogin() + "  for add money");
        int toToBillIndex = in.nextInt() - 1;

        System.out.println("Enter the transaction summa");
        int transactionSumma = in.nextInt();

        int idToBill = billListToUser.get(toToBillIndex).getId();
        billService.transactionToBill(idFromBill, idToBill, transactionSumma);
        transferService.addTransfer(lastUser, toUser, idFromBill, idToBill, transactionSumma);

        List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
        printBillWithUserAndBalance(lastFromBillList, lastUser);

        List<Bill> lastToBillList = billService.findBillsByUser(toUser);
        printBillWithUserAndBalance(lastToBillList, toUser);
    }

    private void printBillWithUserAndBalance(List<Bill> lastFromBillList, User lastUser) {
        for (Bill fromBillInList : lastFromBillList) {
            System.out.println(lastUser.getName() + ". name of bill:   " + fromBillInList.getName() + ".   balance =  " + fromBillInList.getBalance());
        }
    }

    private void transactionToRandomBill(User lastUser, User toUser) {
        try {
            int idFromBill = choiceFromBillId(lastUser);
            System.out.println("Enter the transaction summa");
            int transactionSumma = in.nextInt();
            List<Bill> billListToUser = billService.findBillsByUser(toUser);
            int idToBill = billListToUser.get((int) (billListToUser.size() * Math.random())).getId();
            billService.transactionToBill(idFromBill, idToBill, transactionSumma);
            transferService.addTransfer(lastUser, toUser, idFromBill, idToBill, transactionSumma);

            List<Bill> lastFromBillList = billService.findBillsByUser(lastUser);
            printBillWithUserAndBalance(lastFromBillList, lastUser);
            List<Bill> lastToBillList = billService.findBillsByUser(toUser);
            printBillWithUserAndBalance(lastToBillList, toUser);
        } catch (MyExceptionBill e) {
            System.out.println(e.getMessage());
        }
    }

    private int choiceFromBillId(User lastUser) {
        List<Bill> billListFromUser = billService.findBillsByUser(lastUser);
        printBillsWithBalance(billListFromUser);

        System.out.println("Enter Bill for write off money");
        int fromBillIndex = in.nextInt() - 1;
        int idFromBill = billListFromUser.get(fromBillIndex).getId();
        return idFromBill;
    }

    private void printBillTransaction(User lastUser) {
        List<Bill> billList = billService.findBillsByUser(lastUser);

        printBillsWithBalance(billList);
        System.out.println("Enter ID Bill for print transactions");
        int printBillIndex = in.nextInt() - 1;
        int idBillForPrint = billList.get(printBillIndex).getId();

        List<Transfer> transferList = transferService.findTransferByBillsId(idBillForPrint);

        for (Transfer transferInList : transferList) {
            String nameFromUser = userService.findUserById(transferInList.getIdFromUser()).getName();
            String nameFromBill = billService.findBillById(transferInList.getIdFromBill()).getName();
            int sumTransaction = transferInList.getSumTransaction();
            String nameToUser = userService.findUserById(transferInList.getIdToUser()).getName();
            String nameToBill = billService.findBillById(transferInList.getIdToBill()).getName();
            Timestamp timeDateTransaction = transferInList.getTimeDateTransaction();

            System.out.println("User From   " + nameFromUser + "    Bill From   " + nameFromBill + "   Sum Transaction   = " + sumTransaction + "   User To   " + nameToUser + "   Bill To   " + nameToBill + "Time Transaction   " + timeDateTransaction);
        }
    }
}