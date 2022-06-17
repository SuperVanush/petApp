package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class UserStorage implements Storage<User> {
    List<User> userList = new ArrayList<>();

    @Override
    public User add(User user) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
            String sql = "insert into users ( user_name, login) VALUES (?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql);
            psmt.setString(1, user.getName());
            psmt.setString(2, user.getLogin());
            psmt.executeUpdate();
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
    public User findByLogin(String login) {
        String sql = "select * from users where login = ? ";
        User user = null;
        try {
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "5577166");
            Statement statement = connect.createStatement();

            try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setString(1, login);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLogin(resultSet.getString("login"));
                        connect.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
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