package com.example.demo.dao.implstorage;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class UserStorage implements StorageUser {

    @Override
    public User add(User user) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
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
    public User findByLogin(String login) {
        User user = null;
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql:??localhost:5432//postgres",
                "postgres",
                "5577166")) {
            String sqlRequest = "select * from users where login = ?";
            PreparedStatement psmt = connect.prepareStatement(sqlRequest);
            psmt.setString(1, login);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String userLogin = resultSet.getString("login");
                user = new User(id, name, userLogin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getListOfElements() {
        List<User> userList = new ArrayList<>();
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
            String sql = "select * from users left join bills b on users.user_id = b.user_id";
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
    public void remove(int id) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
            String sqlRequest = "select * from users";
            ResultSet resultSet = statement.executeQuery(sqlRequest);
            while ((resultSet.next())) {
                int userId = resultSet.getInt("user_id");
                if (userId == id) {
                    String sqlRemoveRequest = "delete from users where user_id = ?";
                    PreparedStatement psmt = connect.prepareStatement(sqlRemoveRequest);
                    psmt.setInt(1, userId);
                    psmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}