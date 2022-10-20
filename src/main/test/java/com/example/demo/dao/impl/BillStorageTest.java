package com.example.demo.dao.impl;

import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static com.example.demo.factory.Factory.getUserStorageInstance;

public class BillStorageTest extends TestCase {

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
    public void addBill (){
        Bill bill = new Bill();
        bill.setName("qqq");
        bill.setBalance(55);
        subj.add(bill);
    }
}