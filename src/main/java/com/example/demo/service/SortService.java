package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class SortService {

    private final UserComparison sortUser = new UserComparison();
    private final Scanner in = new Scanner(System.in);

    public void startSortUsers(List<User> userList) {
        int choiceNumberSort;
        String continueChoice;
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
        System.out.println("MENU");
        System.out.println("1. Sort users");
        System.out.println("2. Other action");
        System.out.println("Make your choice");
        int actionChoice = in.nextInt();
        if (actionChoice == 1) {
            do {
                System.out.println("Add sort metod");
                System.out.println("1. Sort by name of users");
                System.out.println("2. Sort by ID of users");

                choiceNumberSort = in.nextInt();
                if (choiceNumberSort == 1) {
                    Collections.sort(userList, getMap.get(EnumComparatorMap.SORT_BY_NAME.getValue()));
                    System.out.println(userList);
                }
                if (choiceNumberSort == 2) {
                    Collections.sort(userList, getMap.get(EnumComparatorMap.SORT_BY_NUMBER.getValue()));
                    System.out.println(userList);
                }
                System.out.println("Do you want to continiue ?   y/n");
                continueChoice = in.next();
            }
            while (continueChoice.equals("y"));
        } else {
            System.out.println("other choice is wrong");
        }
    }
}
