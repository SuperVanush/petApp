package com.example.demo.dao.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.view.ViewConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
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
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
        subj = context.getBean(BillStorage.class);
        userStorage = context.getBean(UserStorage.class);
    }

    @Test
    public void testAddBill() {
        User user = User.builder().build();
        user.setName("qqq");
        user.setLogin("qqq");

        Bill bill = Bill.builder().build();
        bill.setName("bill_qqq");
        bill.setBalance(BigDecimal.valueOf(55));

        User addedUser = userStorage.add(user);
        bill.setUser(addedUser);

        Bill addedBill = subj.add(bill);
        Bill billFromBd = subj.findBillFromId(addedBill.getId());

        assertEquals(addedBill, billFromBd);
    }

    @Test
    public void testGetListOfElements() {
        User user1 = User.builder().build();
        user1.setName("user1");
        user1.setLogin("loginUser1");

        Bill bill1 = Bill.builder().build();
        bill1.setName("billUser1");
        bill1.setBalance(BigDecimal.valueOf(789));

        User addedUser1 = userStorage.add(user1);
        bill1.setUser(addedUser1);

        List<Bill> testBillList = new ArrayList<>();
        testBillList.add(bill1);

        subj.add(bill1);
        List<Bill> listFromBD = subj.getListOfElements();
        assertEquals(testBillList, listFromBD);
    }

    @Test
    public void testFindBillFromId() {
        User user1 = User.builder().build();
        user1.setName("User1");
        user1.setLogin("LoginUser1");

        Bill bill1 = Bill.builder().build();
        bill1.setName("BillUser1");
        bill1.setBalance(BigDecimal.valueOf(55));

        User user2 = User.builder().build();
        user2.setName("User2");
        user2.setLogin("LoginUser2");

        Bill bill2 = Bill.builder().build();
        bill2.setName("BillUser2");
        bill2.setBalance(BigDecimal.valueOf(999));

        User user3 = User.builder().build();
        user3.setName("User3");
        user3.setLogin("LoginUser3");

        Bill bill3 = Bill.builder().build();
        bill3.setName("BillUser3");
        bill3.setBalance(BigDecimal.valueOf(777));

        User addedUser1 = userStorage.add(user1);
        bill1.setUser(addedUser1);
        User addedUser2 = userStorage.add(user2);
        bill2.setUser(addedUser2);
        User addedUser3 = userStorage.add(user3);
        bill3.setUser(addedUser3);

        Bill addedBill1 = subj.add(bill1);
        Bill addedBill2 = subj.add(bill2);
        Bill addedBill3 = subj.add(bill3);
        Bill billFromBd1 = subj.findBillFromId(addedBill1.getId());
        Bill billFromBd2 = subj.findBillFromId(addedBill2.getId());
        Bill billFromBd3 = subj.findBillFromId(addedBill3.getId());

        assertEquals(addedBill3, billFromBd3);
    }
}