package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public final class UserStorage implements Storage<User> {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    public static final String DB_Drivers = "DB_Drivers";

    private final List<User> userList = new ArrayList<>();

    @Override
    public User add(User user) {
        try {
            Class.forName(DB_Drivers);
            Connection connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected");
            connection.close();
            System.out.println("Disconnected");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver is not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL ERROR");
        }
        if (userList.isEmpty()) {
            user.setId(1);
        } else {
            int maxId = userList.get(0).getId();
            for (User userInList : userList) {
                int maxNextId = userInList.getId();
                if (maxNextId > maxId)
                    maxId = maxNextId;
                user.setId(maxId + 1);
            }
        }
        userList.add(user);
        return user;
    }

    @Override
    public User findById(int id) {
        for (User userInList : userList) {
            if (userInList.getId() == id) {
                return userInList;
            }
        }
        throw new UserListException("User is not found");
    }

    @Override
    public void printAll() {
        userList.forEach(System.out::println);
    }

    @Override
    public List<User> getListOfElements() {
        return userList;
    }

    @Override
    public void remove(int id) throws UserListException {
        int indexOfDeleteUser;
        boolean isUserDeleted = false;
        for (User userInList : userList) {
            if (userInList.getId() == id) {
                indexOfDeleteUser = userList.indexOf(userInList);
                userList.remove(indexOfDeleteUser);
                isUserDeleted = true;
                break;
            }
        }
        if (!isUserDeleted) {
            throw new UserListException("User is not found");
        }
    }
}