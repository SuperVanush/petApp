package com.example.demo.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;
@Data
public class User {

    private int id;
    private String name;
    private String login;
    private String password;
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

    public User(int id, String name, String login, String password) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(password, user.password) &&
                Objects.equals(login, user.login) && Objects.equals(bills, user.bills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, login, bills);
    }
}