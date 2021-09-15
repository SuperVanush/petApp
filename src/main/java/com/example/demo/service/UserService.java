package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.util.Scanner;

public class UserService {
    private final SortService sortService = new SortService();
    private final Scanner in = new Scanner(System.in);
    private final Storage<User> userStorage = new UserStorage();

    public void startApp() {
        String choiceAddUser;
        String userName;
        int userId;
        do {
            System.out.println("Input name of user");
            userName = in.next();

            System.out.println("Input ID of user");
            userId = in.nextInt();
            try {
                userStorage.add(new User(userId, userName));
            }
            catch (UserListException e){
            System.out.println(e.getMessage());
            }
            System.out.println("Do you want add next user? y/n");
            choiceAddUser = in.next();
        }
        while (choiceAddUser.equals("y"));
        sortService.startSortUsers(userStorage.getListOfElements());
    }
}


