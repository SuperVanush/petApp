package com.example.demo.model;

public class User implements Comparable<User> {
    private Bill bill;
    private int id;
    private String name;
    private String billName=bill.getBillName();
    private int billId = bill.getBillId();
    private int billBalance = bill.getBillBalance();

    public User(String name) {
        this.name = name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id, String name, String billName, int billId, int billBalance) {
        this.id = id;
        this.name = name;
        this.billName = billName;
        this.billId = billId;
        this.billBalance = billBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(id, o.getId());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}


