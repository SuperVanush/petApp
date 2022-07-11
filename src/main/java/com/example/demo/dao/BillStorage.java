package com.example.demo.dao;

import com.example.demo.exception.BillListException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillStorage implements StorageBill<Bill> {

    @Override
    public Bill add(Bill bill) {
        try (Connection connect = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "5577166")) {
            Statement statement = connect.createStatement();
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
    public Bill findById(int id) throws BillListException {
        List<Bill> billList = getListOfElements();
        for (Bill billInList : billList) {
            if (billInList.getId() == id) {
                return billInList;
            }
        }
        throw new BillListException("Bill is not found");
    }

    @Override
    public void remove(int id) throws BillListException {
    }

    @Override
    public void printAll() {
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


