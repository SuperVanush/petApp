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
        User user = new User();
        user.setName("qqq");
        user.setLogin("qqq");

        Bill bill = new Bill();
        bill.setName("bill_qqq");
        bill.setBalance(55);

        User addedUser = userStorage.add(user);
        bill.setUser(addedUser);

        Bill addedBill = subj.add(bill);
        Bill billFromBd = subj.findBillFromId(addedBill.getId());

        assertEquals(addedBill, billFromBd);
    }
}