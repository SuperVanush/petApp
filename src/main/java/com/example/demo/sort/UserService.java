package com.example.demo.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService {
    private SortUsers sortUsers = new SortUsers();
    private final Scanner in = new Scanner(System.in);
    private  List<com.example.demo.sort.User> userList = new ArrayList<>();

    public void startApp() {
        String choiceAddUser;
        String userName;
        int userId ;
        String alphabet;
        do {
            System.out.println("Input name of user");
            userName = in.next();
            alphabet = "^[a-zA-Zа-яА-Я]*$";
            Pattern pattern = Pattern.compile(alphabet);
            Matcher matcher = pattern.matcher(userName);
            if (!matcher.matches()) {
                System.out.println("This is not text. Try again");
            }}
            while (!userName.equals(alphabet) );
               do { System.out.println("Input ID of user");
                userId = in.nextInt();
            userList.add(new com.example.demo.sort.User(userId, userName));
            if (userList.contains(userId)){
                System.out.println("Input other ID");
                userId= in.nextInt();
            }
            System.out.println("Do you want add next user? y/n");
            choiceAddUser = in.next();
                if (!choiceAddUser.equals("y")|| (!choiceAddUser.equals("n"))){
                System.out.println("Press inpur y / n");
                choiceAddUser = in.next ();
            }
            }
            while (choiceAddUser.equals("y")) ;
            sortUsers.startTwo(userList);
    }
        }


