package com.example.demo.service.impl;

import com.example.demo.dao.StorageUser;
import com.example.demo.dao.impl.UserStorage;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
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
    public void setUp() throws NoSuchFieldException {
        userStorage = mock(UserStorage.class);
        billService = mock(BillService.class);
        Field fieldUserStorage = subj.getClass().getDeclaredField("userStorage");
        Field fieldBillService = subj.getClass().getDeclaredField("billService");
        FieldSetter fieldSetterUserStorage = new FieldSetter(subj, fieldUserStorage);
        FieldSetter fieldSetterBillService = new FieldSetter(subj, fieldBillService);
        fieldSetterUserStorage.set(userStorage);
        fieldSetterBillService.set(billService);
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
        User userFromService = subj.findUserByLogin("qqq");
        assertEquals(user, userFromService);
    }

    @Test
    public void testRemoveUser() {
    }
}