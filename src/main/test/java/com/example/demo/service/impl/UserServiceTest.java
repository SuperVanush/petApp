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

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

    UserService subj;
    StorageUser userStorage;
    BillService billService;

    @Before
    public void setUp() throws Exception {
        userStorage = mock(StorageUser.class);
        billService = mock(BillService.class);
      //  subj = new UserService((UserStorage) userStorage, billService);
    }

    @Test
    public void test_AddUser() {
        User user = new User();
        user.setName("qqq");
        user.setLogin("qqq");

        User userFromDatabase = new User();
        userFromDatabase.setLogin("qqq");
        userFromDatabase.setName("qqq");

        when(userStorage.add(user)).thenReturn(userFromDatabase);
        User userFromService = subj.addUser("qqq", "qqq");
        assertEquals(user, userFromService);
    }

    @Test
    public void test_FindUserByLogin_notFindUser() {
        when(userStorage.findByLogin("rrr")).thenReturn(null);
        User user = subj.findUserByLogin("rrr");
        assertNull(user);
    }

    @Test
    public void test_FindUserByLogin_ok() {
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
    public void test_RemoveUser_Ok() {
        User user = new User();
        user.setLogin("ddd");
        user.setId(1);

        when(subj.findUserByLogin("ddd")).thenReturn(user);
        userStorage.remove(1);
        verify(userStorage).remove(1);
    }
}