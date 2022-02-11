package com.example.demo.model;

import com.example.demo.dao.BillStorage;

import java.util.List;

public class User implements Comparable<User> {
    private int id;
    private String name;
    private List<Bill> billList;

    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "billList=" + billList +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(User o) {
        return Integer.compare(id, o.getId());
    }


}




