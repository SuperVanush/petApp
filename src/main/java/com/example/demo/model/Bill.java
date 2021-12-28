package com.example.demo.model;

public class Bill {
    private String billName;
    private int billId;
    private int billBalance;

    public Bill(String billName, int billId, int billBalance) {
        this.billName = billName;
        this.billId = billId;
        this.billBalance = billBalance;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public int getBillBalance() {
        return billBalance;
    }

    public void setBillBalance(int billBalance) {
        this.billBalance = billBalance;
    }
}
