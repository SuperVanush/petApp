package com.example.demo.dao.impl;

import com.example.demo.dao.DaoConfiguration;
import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillStorage implements StorageBill {
    private final DaoConfiguration daoConfiguration;

    public BillStorage() {
        daoConfiguration = new DaoConfiguration();
    }

    @Override
    public Bill add(Bill bill) {
        try (Connection connect = daoConfiguration.dataSource().getConnection()) {
            String sql = "insert into bills (bill_name, bill_balance, user_id) VALUES (?,?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, bill.getName());
            psmt.setInt(2, bill.getBalance());
            psmt.setInt(3, bill.getUser().getId());
            int affectedRows = psmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bill.setId(Math.toIntExact(generatedKeys.getLong(1)));
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    @Override
    public List<Bill> getListOfElements() {
        List<Bill> billList = new ArrayList<>();
        try (Connection connection = daoConfiguration.dataSource().getConnection()) {
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

    @Override
    public Bill findBillFromId(int idBill) {
        Bill bill = null;
        try (Connection connection = daoConfiguration.dataSource().getConnection()) {
            String sglResultRequest = "select * from bills where bill_id = ?";
            PreparedStatement psmtResult = connection.prepareStatement(sglResultRequest);
            psmtResult.setInt(1, idBill);
            ResultSet resultSet = psmtResult.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("bill_id");
                String billname = resultSet.getString("bill_name");
                int balance = resultSet.getInt("bill_balance");
                int userId = resultSet.getInt("user_id");
                User user = new User(userId);
                bill = new Bill(billname, id, balance, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    @Override
    public void updateBill(Bill bill) {
        int balanceBill = bill.getBalance();
        int idBill = bill.getId();
        try (Connection connection = daoConfiguration.dataSource().getConnection()) {
            String sqlReqest = "update bills set bill_balance = ? where bill_id = ?";
            PreparedStatement psmt = connection.prepareStatement(sqlReqest);
            psmt.setInt(1, balanceBill);
            psmt.setInt(2, idBill);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}