package com.example.demo.dao;

import com.example.demo.exception.UserListException;
import com.example.demo.model.Bill;

import java.util.List;

public final class BillStorage implements Storage <Bill>{


    @Override
    public void add(Bill bill) {

    }

    @Override
    public void remove(int id) throws UserListException {

    }

    @Override
    public void printAll() {

    }

    @Override
    public List<Bill> getListOfElements() {
        return null;
    }
}

