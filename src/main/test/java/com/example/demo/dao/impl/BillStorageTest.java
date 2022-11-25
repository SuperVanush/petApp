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

        Bill testedBill = new Bill();
        testedBill.setId(addedBill.getId());
        testedBill.setName(addedBill.getName());
        testedBill.setBalance(addedBill.getBalance()); // создала счет без пользователя,
                                                        // но с остальными полями, что бы пользователь был null

        Bill billFromBd = subj.findBillFromId(addedBill.getId());

        Bill testedBillFromBd = new Bill();
        testedBillFromBd.setId(billFromBd.getId());
        testedBillFromBd.setName(billFromBd.getName());
        testedBillFromBd.setBalance(billFromBd.getBalance()); // создала еще один счет с данными от счета из БД, но без пользователя.

        assertEquals(testedBill,testedBillFromBd);   // сравнила двух этих пользователей.
                                                    // НО не работает тест. все поля одинаковые, а пользователи - null -это меня смущает -вот сверка лажает как будто
    }
}