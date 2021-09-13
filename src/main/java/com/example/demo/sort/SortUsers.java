package com.example.demo.sort;

import java.util.*;

public class SortUsers {

    private UserComparison sortUser = new UserComparison();
    private final Scanner in = new Scanner(System.in);

    public void startTwo(List<User> userList) {
        int choiceNumberSort;
        String continueChoice;
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
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
    }
}
