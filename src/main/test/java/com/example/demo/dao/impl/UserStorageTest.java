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

@RunWith(MockitoJUnitRunner.class)
public class UserStorageTest extends TestCase {
    UserStorage subj;
    BillStorage billStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
        subj = context.getBean(UserStorage.class);
        billStorage = context.getBean(BillStorage.class);
    }

    @Test
    public void testAddUser() {
        User user = new User();
        user.setLogin("User1");
        user.setName("User1");

        Bill bill = new Bill();
        bill.setName("billName");
        bill.setBalance(555);


    }
}
