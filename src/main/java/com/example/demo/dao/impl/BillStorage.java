package com.example.demo.dao.impl;

import com.example.demo.dao.StorageBill;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillStorage implements StorageBill {

    private final DataSource dataSource;

    public BillStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Bill add(Bill bill) {
        try (Connection connect = dataSource.getConnection()) {
            String sql = "insert into bills (bill_name, bill_balance, user_id) VALUES (?,?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, bill.getName());
            psmt.setInt(3, bill.getUser().getId());
            int affectedRows = psmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }
            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bill = Bill.builder()
                            .id(Math.toIntExact(generatedKeys.getLong(1))).build();
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
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from bills left outer join users u on u.user_id = bills.user_id";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("bill_id");
                String name = resultSet.getString("bill_name");
                BigDecimal balance = resultSet.getBigDecimal("bill_balance");
                int userId = resultSet.getInt("user_id");
                User user = User.builder()
                        .id(userId).build();
                Bill bill = Bill.builder()
                        .name(name).id(id).balance(balance).user(user).build();
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
        try (Connection connection = dataSource.getConnection()) {
            String sglResultRequest = "select * from bills where bill_id = ?";
            PreparedStatement psmtResult = connection.prepareStatement(sglResultRequest);
            psmtResult.setInt(1, idBill);
            ResultSet resultSet = psmtResult.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("bill_id");
                String billname = resultSet.getString("bill_name");
                BigDecimal balance = resultSet.getBigDecimal("bill_balance");
                int userId = resultSet.getInt("user_id");
                User user = User.builder()
                        .id(userId).build();
                bill = Bill.builder()
                        .name(billname).id(id)
                        .balance(balance).user(user).build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bill;
    }

    @Override
    public void updateBill(Bill bill) {
        BigDecimal balanceBill = bill.getBalance();
        int idBill = bill.getId();
        try (Connection connection = dataSource.getConnection()) {
            String sqlRequest = "update bills set bill_balance = ? where bill_id = ?";
            PreparedStatement psmt = connection.prepareStatement(sqlRequest);
            psmt.setBigDecimal(1, balanceBill);
            psmt.setInt(2, idBill);
            psmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}