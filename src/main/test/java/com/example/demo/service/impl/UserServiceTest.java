package com.example.demo.service.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {


    @InjectMocks
    UserService subj;
    @Mock
    StorageUser userStorage;
    @Mock
    BillService billService;

    @Before
    public void setUp() {
        userStorage = mock(StorageUser.class);
        billService = mock(BillService.class);
    }

    @Test
    public void testAddUser() {
    }

    @Test
    public void test_notFindUser() {
        when(userStorage.findByLogin("qqq")).thenReturn(null);
        User user = subj.findUserByLogin("qqq");
        assertNull(user);
    }

    @Test
    public void test_ok() {
        User user = new User();
        user.setId(1);
        Bill bill = new Bill();
        bill.setName("Billname");
        List<Bill> bills = new ArrayList<>();
        bills.set(1, bill);
        User userByLogin = new User();
        userByLogin.setId(1);
        userByLogin.setLogin("qqq");
        when(userStorage.findByLogin("qqq")).thenReturn(userByLogin);
        when(billService.findBillsByUser(userByLogin)).thenReturn(bills);
        User userFromServise = subj.findUserByLogin("qqq");
        assertEquals(user, userFromServise);
    }

    @Test
    public void testRemoveUser() {
    }
}