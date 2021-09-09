package com.example.demo.sort;

import java.util.*;
import java.util.Comparator;

public class UserService {
    private final Scanner in = new Scanner(System.in);
    UserComparison sortUser = new UserComparison();
    List<User> userList = new ArrayList<>();

    public void startApp() {
        String choiceAddUser;
        String nameUser;
        int numberUser;
        do {
            System.out.println("Input name of user");
            nameUser = in.next();
            System.out.println("Input ID of user");
            numberUser = in.nextInt();
            System.out.println("Do you want add next user? y/n");
            choiceAddUser = in.next();
            userList.add(new User(nameUser, numberUser));
        }
        while (choiceAddUser.equals("y"));
        System.out.println(userList);
        startTwo();
    }

    public void startTwo() {
        int choiceNumberSort;
        String continueChoice;
        Map<String, Comparator<User>> getMap = sortUser.getComparatorMap();
        do {
            System.out.println("Add sort metod");
            System.out.println("1. Sort by name of users");
            System.out.println("2. Sort by ID of users");
            choiceNumberSort = in.nextInt();

            if (choiceNumberSort == 1) {
                Collections.sort(userList, getMap.get(EnumComparatorMap.SORTBYNAME.getValue()));
                System.out.println(userList);
            }
            if (choiceNumberSort == 2) {
                Collections.sort(userList, getMap.get(EnumComparatorMap.SORTBYNUMBER.getValue()));
                System.out.println(userList);
            }
            System.out.println( "Do you want to continiue ?   y/n");
            continueChoice = in.next();
        }
        while (continueChoice.equals("y"));
    }
}
