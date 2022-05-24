package com.example.demo.model;

public class Bill {
    private String name;
    private int id;
    private int balance;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }
}
