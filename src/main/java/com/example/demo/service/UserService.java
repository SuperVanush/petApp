package com.example.demo.service;

import com.example.demo.dao.Storage;
import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.sql.*;
import java.util.List;

public class UserService {

    private final Storage<User> userStorage = Factory.getUserStorageInstance();

    public User addUser(String name, String login) {
        User user = new User();
        user.setName(name);
        user.setLogin(login);
        user = userStorage.add(user);
        return user;
    }

    public void rewriteUser(List<Bill> bills, User lastUser) {
        lastUser.setBills(bills);
    }

    public User findUserByLogin(String login) {
        String sql = "select * from users where login = ? ";
        User user = null;
        try {
            Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "5577166");
            Statement statement = connect.createStatement();

            try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setString(1, login);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        user = new User();
                        user.setId(resultSet.getInt("user_id"));
                        user.setName(resultSet.getString("user_name"));
                        user.setLogin(resultSet.getString(login));
                        connect.close();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}