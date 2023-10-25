package com.example.demo.dao.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BillStorageTest extends TestCase {
    BillStorage subj;
    UserStorage userStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");
        ApplicationContext context = new AnnotationConfigApplicationContext();
        subj = context.getBean(BillStorage.class);
        userStorage = context.getBean(UserStorage.class);
    }

    @Test
    public void testAddBill() {
        User user = User.builder().username("qqq").login("qqq").build();

        Bill bill;

        User addedUser = userStorage.add(user);
        bill = Bill.builder().user(addedUser).build();

        Bill addedBill = subj.add(bill);
        Bill billFromBd = subj.findBillFromId(addedBill.getId());

        assertEquals(addedBill, billFromBd);
    }

    @Test
    public void testGetListOfElements() {
        User user1 = User.builder().username("user1").login("loginUser1").build();

        Bill bill1;

        User addedUser1 = userStorage.add(user1);
        bill1 = Bill.builder().user(addedUser1).build();

        List<Bill> testBillList = new ArrayList<>();
        testBillList.add(bill1);

        subj.add(bill1);
        List<Bill> listFromBD = subj.getListOfElements();
        assertEquals(testBillList, listFromBD);
    }

    @Test
    public void testFindBillFromId() {
        User user1 = User.builder().username("User1").login("LoginUser1").build();

        Bill bill1;

        User user2 = User.builder().username("User2").login("LoginUser2").build();

        Bill bill2;

        User user3 = User.builder().username("User3").login("LoginUser3").build();

        Bill bill3;

        User addedUser1 = userStorage.add(user1);
        bill1 = Bill.builder().user(addedUser1).build();
        User addedUser2 = userStorage.add(user2);
        bill2 = Bill.builder().user(addedUser2).build();
        User addedUser3 = userStorage.add(user3);
        bill3 = Bill.builder().user(addedUser3).build();

        Bill addedBill1 = subj.add(bill1);
        Bill addedBill2 = subj.add(bill2);
        Bill addedBill3 = subj.add(bill3);
        Bill billFromBd1 = subj.findBillFromId(addedBill1.getId());
        Bill billFromBd2 = subj.findBillFromId(addedBill2.getId());
        Bill billFromBd3 = subj.findBillFromId(addedBill3.getId());

        assertEquals(addedBill3, billFromBd3);
    }
}