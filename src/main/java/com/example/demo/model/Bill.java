package com.example.demo.model;

public class Bill {

    private String name;
    private int id;
    private int balance;
    private User user;

    public Bill() {
    }

    public Bill(int id, String name, int balance) {
        this.name = name;
        this.id = id;
        this.balance = balance;
    }

    public Bill(String name, int id, int balance, User user) {
        this.name = name;
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

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