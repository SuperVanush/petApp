package com.example.demo.model;

import javax.xml.crypto.Data;
import java.util.Date;

public class TransactionObj {

    private String userName;
    private String billName;
    private int TransactionSum;
    private int id;
    Data transactionData;

    public TransactionObj(String userName, String billName, int transactionSum, Data transactionData, int id) {
        this.userName = userName;
        this.billName = billName;
        TransactionSum = transactionSum;
        this.id = id;
        this.transactionData = transactionData;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public int getTransactionSum() {
        return TransactionSum;
    }

    public void setTransactionSum(int transactionSum) {
        TransactionSum = transactionSum;
    }

    public Date getTransactionData() {

        return (Date) transactionData;
    }

    public void setTransactionData(Data transactionData) {
        this.transactionData = transactionData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
