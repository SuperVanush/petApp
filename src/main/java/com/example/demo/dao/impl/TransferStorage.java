package com.example.demo.dao.impl;

import com.example.demo.dao.StorageTransfer;
import com.example.demo.model.Transfer;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransferStorage implements StorageTransfer {

    private DataSource dataSource;

    public TransferStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Transfer add(Transfer transfer) {
        try (Connection connect = dataSource.getConnection()) {
            String sql = "insert into transaction_history (user_from_id, bill_from_id, sum_transaction, " +
                    "user_to_id, bill_to_id,time_date_transaction) VALUES (?,?,?,?,?,?)";
            PreparedStatement psmt = connect.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            psmt.setInt(1, transfer.getIdFromUser());
            psmt.setInt(2, transfer.getIdFromBill());
            psmt.setInt(3, transfer.getSumTransaction());
            psmt.setInt(4, transfer.getIdToUser());
            psmt.setInt(5, transfer.getIdToBill());
            psmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            int affectedRows = psmt.executeUpdate(sql);

            if (affectedRows == 0) {
                throw new SQLException("Creating transaction failed, no rows affected.");
            }
            try (ResultSet generatedKeys = psmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    transfer.setId(Math.toIntExact(generatedKeys.getLong(1)));
                } else {
                    throw new SQLException("Creating transaction failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transfer;
    }

    @Override
    public List<Transfer> getListOfElements() {
        List<Transfer> transferList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            String sql = "select * from transaction_history";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int idFromUser = resultSet.getInt("user_from_id");
                int idFromBill = resultSet.getInt("bill_from_id");
                int sumTransaction = resultSet.getInt("sum_transaction");
                int idToUser = resultSet.getInt("user_to_id");
                int idToBill = resultSet.getInt("bill_to_id");
                Timestamp timeDateTransaction = resultSet.getTimestamp("time_date_transaction");
                LocalDateTime localDateTime = timeDateTransaction.toLocalDateTime();
                Transfer transfer = new Transfer(idFromUser, idFromBill, idToUser, idToBill,
                        sumTransaction, localDateTime);
                transferList.add(transfer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transferList;

    }

    @Override
    public Transfer findTransferFromId(int idTransfer) {
        return null;
    }

    @Override
    public void updateTransfer(Transfer transfer) {

    }
}
