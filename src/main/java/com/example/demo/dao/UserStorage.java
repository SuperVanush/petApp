package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class UserStorage implements Storage<User> {
    private PreparedStatement psmt = null;

    private final List<User> userList = new ArrayList<>();

    @Override
    public User add(User user) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
            String sql = ("insert into users (user_name, login) VALUES (?,?)");
            psmt = connect.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                System.out.println(
                        resultSet.getInt("user_id" + "user_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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