package com.example.demo.service.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

    UserService subj;
    StorageUser userStorage;
    BillService billService;

    @Before
    public void setUp() throws Exception {
        userStorage = mock(StorageUser.class);
        billService = mock(BillService.class);
        subj = new UserService(userStorage, billService);
    }

    @Test
    public void testAddUser() {
    }

    @Test
    public void test_notFindUser() {
        when(userStorage.findByLogin("rrr")).thenReturn(null);
        User user = subj.findUserByLogin("rrr");
        assertNull(user);
    }

    @Test
    public void test_ok() {
        User userByLogin = new User();
        userByLogin.setId(1);
        userByLogin.setLogin("qqq");
        Bill bill = new Bill();
        List<Bill> bills = new ArrayList<>();
        bills.add(0, bill);
        when(userStorage.findByLogin("qqq")).thenReturn(userByLogin);
        when(billService.findBillsByUser(userByLogin)).thenReturn(bills);
        User userFromService = subj.findUserByLogin("qqq");
        assertEquals(userByLogin, userFromService);
    }


    @Test
    public void testRemoveUser() {
    }
}