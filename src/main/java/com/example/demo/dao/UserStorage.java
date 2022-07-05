package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class UserStorage implements Storage<User> {

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
        User user = null;
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            String sql = "select * from users where user_id = ?";
            PreparedStatement psmt = connect.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet resultSet = psmt.executeQuery(sql);
            while (resultSet.next()) {
                id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String login = resultSet.getString("login");
                user = new User(id, name, login);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void printAll() {
        List<User> userList = new ArrayList<>();
        userList = getListOfElements();
    }

    @Override
    public List<User> getListOfElements() {
        List<User> userList = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
            String sql = "select * from users";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String login = resultSet.getString("login");
                User user = new User(id, name, login);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void remove(int id) throws UserListException {
        List<User> userList = getListOfElements();
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