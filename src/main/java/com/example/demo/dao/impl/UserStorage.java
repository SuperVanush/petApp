package com.example.demo.dao.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserStorage implements StorageUser {

    private final DataSource dataSource;

    public UserStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User add(User user) {
        try (Connection connect = dataSource.getConnection()) {
            String sql = "insert into users ( user_name, login, password) VALUES (?,?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, user.getName());
            psmt.setString(2, user.getLogin());
            psmt.setString(3, user.getPassword());
            int affectedRowsUser = psmt.executeUpdate();

            if (affectedRowsUser == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
            try (ResultSet generatedKeysUser = psmt.getGeneratedKeys()) {
                if (generatedKeysUser.next()) {
                    user.setId(Math.toIntExact(generatedKeysUser.getLong(1)));
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection connect = dataSource.getConnection()) {
            String sql = "select * from users where user_id = ?";
            PreparedStatement psmt = connect.prepareStatement(sql);
            psmt.setInt(1, id);
            ResultSet resultSet = psmt.executeQuery();
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
        try (Connection connect = dataSource.getConnection()) {
            String sqlRequest = "select * from users where login = ?";
            PreparedStatement psmt = connect.prepareStatement(sqlRequest);
            psmt.setString(1, login);
            ResultSet resultSet = psmt.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("user_id");
                String name = resultSet.getString("user_name");
                String userLogin = resultSet.getString("login");
                String password = resultSet.getString("password");
                user = new User(id, name, userLogin, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    @Override
    public List<User> getListOfElements() {
        List<User> userList = new ArrayList<>();
        try (Connection connect = dataSource.getConnection()) {
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
        try (Connection connect = dataSource.getConnection()) {
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