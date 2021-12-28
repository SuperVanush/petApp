package com.example.demo.model;
import com.example.demo.model.Bill;

public class User implements Comparable<User> {
    private Bill bill;
    private int id;
    private String name;
    private String billName;
    private int billId;
    private int billBalance;

        public User (int id, String name,String billName,int billBalance,int billId) {
        this.id= id;
        this.name =name;
        this.billName = bill.getbillName();
        this.billId = bill.getBillId();
        this.billBalance= bill.getBillBalance();
            }
    public String getName() {
        return name;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
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


