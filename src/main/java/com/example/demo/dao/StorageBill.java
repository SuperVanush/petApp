package com.example.demo.dao;

import java.util.List;

public interface StorageBill <Bill> {
    Bill add(Bill bill);

    Bill findById(int id);

    void printAll();

    List<Bill> getListOfElements();

    void remove(int id);
}
