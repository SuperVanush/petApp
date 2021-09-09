package com.example.demo.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private final Scanner in = new Scanner(System.in);
    List<com.example.demo.sort.User> userList = new ArrayList<>();
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
            userList.add(new com.example.demo.sort.User(nameUser, numberUser));
        }
        while (choiceAddUser.equals("y"));
SortUsers sortUsers = new SortUsers();
sortUsers.startTwo (userList);
    }

}

