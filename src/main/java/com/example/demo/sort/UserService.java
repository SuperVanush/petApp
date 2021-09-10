package com.example.demo.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    SortUsers sortUsers = new SortUsers();
    private final Scanner in = new Scanner(System.in);
    List<com.example.demo.sort.User> userList = new ArrayList<>();

    public void startApp() {
        String choiceAddUser;
        String userName;
        int userId;
        do {
            System.out.println("Input name of user");
            userName = in.next();
            for (int elem = 0; elem <= userName.length(); elem++) {
                String alphabet = "^[a-zA-Z]*$";
                Pattern pattern = Pattern.compile(alphabet);
                Matcher matcher = pattern.matcher(userName);
                if (!matcher.matches()) {
                    System.out.println("This is not text. Try again");
                    startApp();
                }}
                System.out.println("Input ID of user");
                userId = in.nextInt();

                System.out.println("Do you want add next user? y/n");
                choiceAddUser = in.next();
                userList.add(new com.example.demo.sort.User(userId, userName));
            }
            while (choiceAddUser.equals("y")) ;
            {
                sortUsers.startTwo(userList);
            }}
        }


