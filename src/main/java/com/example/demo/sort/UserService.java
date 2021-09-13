package com.example.demo.sort;

import com.example.demo.dao.Storage;
import com.example.demo.dao.UserStorage;
import com.example.demo.exception.UserListException;

import java.util.Scanner;

public class UserService {
    private SortUsers sortUsers = new SortUsers();
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

            //TODO Сделать логику по обработке исключений. addUser горит красненьким, обрати внимание)

            try {
                userStorage.add(new User(userId, userName));
            } catch (UserListException e) {
                e.printStackTrace();
            }

            System.out.println("Do you want add next user? y/n");
            choiceAddUser = in.next();
        }
        while (choiceAddUser.equals("y"));
        sortUsers.startTwo(userStorage.getListOfElements());
    }
}


