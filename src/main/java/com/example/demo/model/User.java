package com.example.demo.model;

public class User implements Comparable<User> {
    private Bill bill;
    private int id;
    private String name;

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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

    public String toString(User name) {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' + '}';
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(id, o.getId());
    }


    @Override
    public String toString() {
        return "User{" +
                "bill=" + bill +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}



