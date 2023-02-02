package com.example.demo.dao.impl;

import com.example.demo.dao.StorageTransaction;
import com.example.demo.model.TransactionObj;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionStorage implements StorageTransaction {

    private final DataSource dataSource;

    public TransactionStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<TransactionObj> addListTransaction(TransactionObj transactionObj, int sumTransaction) {
        List<TransactionObj> transactionList = new ArrayList<>();
        try (Connection connect = dataSource.getConnection()) {
            String sql = "insert into transaction_history (bill_name, user_name, sumTransaction, time_date) " +
                    "VALUES (?,?,?,datetime_pl())";
            PreparedStatement psmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setString(1, transactionObj.getUserName());
            psmt.setString(2, transactionObj.getBillName());
            psmt.setInt(3, transactionObj.getTransactionSum());
            int affectedRows = psmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating bill failed, no rows affected.");
            }

            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transactionObj.setId(Math.toIntExact(generatedKeys.getLong(1)));
                } else {
                    throw new SQLException("Creating bill failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionList;
    }


    @Override
    public List<TransactionObj> printTransactionHistory() {
        return null;
    }
}
