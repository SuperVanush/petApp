package com.example.demo.dao.impl;

import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import org.junit.Before;
import org.junit.Test;

import static com.example.demo.factory.Factory.getUserStorageInstance;

public class BillStorageTest {

    BillStorage subj;
    UserStorage userStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");

        subj = (BillStorage) Factory.getBillStorageInstance();
        userStorage = (UserStorage) getUserStorageInstance();
    }

    @Test
    public void addBill() {
        Bill bill = new Bill();
        User user = new User();
        user.setId(1);
        bill.setName("qqq");
        bill.setBalance(55);
        bill.setUser(user);
        subj.add(bill);
    }
}