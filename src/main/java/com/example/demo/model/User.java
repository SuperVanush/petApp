package com.example.demo.model;

import java.util.List;

public class User implements Comparable<User> {

    private int id;
    private String name;
    private String login;
    private List<Bill> bills;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(int id, String name, String login) {
        this.id = id;
        this.name = name;
        this.login = login;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
                ", login='" + login + '\'' +
                ", bills=" + bills +
                '}';
    }
}


