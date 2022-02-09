package com.example.demo.model;

public class Bill {
    private User user;
    private String billName;
    private int billId;
    private int billBalance;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Bill{" +
                "billName='" + billName + '\'' +
                ", billId=" + billId +
                ", billBalance=" + billBalance +
                '}';
    }
}
