package com.example.demo.dao;

import com.example.demo.model.TransactionObj;

import java.util.List;

public interface StorageTransaction {

    List<TransactionObj> addListTransaction(TransactionObj transactionObj, int transactionSum);

    List<TransactionObj> printTransactionHistory();
}
