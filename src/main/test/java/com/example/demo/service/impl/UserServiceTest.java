package com.example.demo.service.impl;

import com.example.demo.dao.impl.UserStorage;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

    UserService subj;
    UserStorage userStorage;
    BillService billService;

    @Before
    public void setUp() throws Exception {
        userStorage = mock(UserStorage.class);
        billService = mock(BillService.class);
        subj = new UserService(userStorage, billService);
    }

    @Test
    public void test_AddUser() {
        User user = User.builder().username("qqq").login("qqq").password("qqq").build();

        User userFromDatabase = User.builder().login("qqq").username("qqq").password("qqq").build();

        when(userStorage.add(user)).thenReturn(userFromDatabase);
        User userFromService = subj.addUser("qqq", "qqq", "qqq");
        assertEquals(user, userFromService);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_FindUserByLogin_notFindUser() {
        User user = subj.findUserByLogin("rrr");
        when(userStorage.findByLogin("rrr")).thenReturn(null);
        assertNull(user);
    }

    @Test
    public void test_FindUserByLogin_ok() {
        User userByLogin = User.builder().id(1).login("qqq").build();

        Bill bill = Bill.builder().build();
        List<Bill> bills = new ArrayList<>();
        bills.add(0, bill);

        when(userStorage.findByLogin("qqq")).thenReturn(Optional.of(userByLogin));
        when(billService.findBillsByUser(userByLogin)).thenReturn(bills);
        User userFromService = subj.findUserByLogin("qqq");
        assertEquals(userByLogin, userFromService);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_RemoveUser_Ok() {
        User user = User.builder().login("ddd").id(1).build();

        when(subj.findUserByLogin("ddd")).thenReturn(user);
        userStorage.remove(user);
        verify(userStorage).remove(user);
    }
}