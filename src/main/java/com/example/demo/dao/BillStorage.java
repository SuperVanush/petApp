package com.example.demo.dao;

import com.example.demo.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillStorage {
    List<Bill> billList;

    public void add (Bill bill){
        billList.add(bill);
    }

    @Override
    public String toString() {
        return "BillStorage{" +
                "billList=" + billList +
                '}';
    }

    public List<Bill> getBillList() {
        return billList;
    }
}
