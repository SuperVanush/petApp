package com.example.demo.dao.implstorage;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillStorage implements StorageBill {

    @Override
    public Bill add(Bill bill) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            String sql = "insert into bills ( bill_name, bill_balance, user_id) VALUES (?,?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql);
            psmt.setString(1, bill.getName());
            psmt.setInt(2, bill.getBalance());
            psmt.setInt(3, bill.getUser().getId());
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    @Override
    public List<Bill> getListOfElements() {
        List<Bill> billList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connection.createStatement();
            String sql = "select * from bills left outer join users u on u.user_id = bills.user_id";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("bill_id");
                String name = resultSet.getString("bill_name");
                int balance = resultSet.getInt("bill_balance");
                int userId = resultSet.getInt("user_id");
                User user = new User(userId);
                Bill bill = new Bill(name, id, balance, user);
                billList.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return billList;
    }
}