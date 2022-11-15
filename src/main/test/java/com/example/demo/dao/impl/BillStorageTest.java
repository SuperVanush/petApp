package com.example.demo.dao.impl;

import com.example.demo.factory.Factory;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BillStorageTest extends TestCase {

    BillStorage subj;
    UserStorage userStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");

        subj = (BillStorage) Factory.getBillStorageInstance();
        userStorage = (UserStorage) Factory.getUserStorageInstance();
    }

    @Test
    public void addBill() {
        Bill bill = new Bill();
        User user = new User();
        user.setId(2);
        user.setName("qqq");
        user.setLogin("qqq");
        bill.setName("bill_qqq");
        bill.setBalance(55);
        bill.setUser(user);
        userStorage.add(user);
        subj.add(bill);
        Bill billFromBd = subj.findBillFromId(bill.getId());
        assertEquals(bill.getId(), billFromBd);
    }
}