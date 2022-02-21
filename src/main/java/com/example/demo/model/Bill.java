package com.example.demo.model;

public class Bill {
    private User user;
    private String name;
    private int id;
    private int balance;

    public User getUser() {
        return user;
    }

    public void setUser(User userStorageById) {
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
                "user=" + user +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", balance=" + balance +
                '}';
    }
}
