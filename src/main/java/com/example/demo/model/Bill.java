package com.example.demo.model;

public class Bill {

    private String nbillName;
    private int billId;

    public Bill(String nbillName, int billId, int billBalance) {
        this.nbillName = nbillName;
        this.billId = billId;
        this.billBalance = billBalance;
    }

    private int billBalance;

    public String getNbillName() {
        return nbillName;
    }

    public void setNbillName(String nbillName) {
        this.nbillName = nbillName;
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
