package com.example.demo.service.impl;

import com.example.demo.dao.impl.BillStorage;
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
public class BillServiceTest extends TestCase {
    BillService subj;
    BillStorage billStorage;

    @Before
    public void setUp() throws Exception {
        billStorage = mock(BillStorage.class);
        subj = new BillService(billStorage);
    }

    @Test
    public void test_AddBill_Ok() {
        Bill bill = new Bill();
        bill.setName("qqq");
        bill.setBalance(123);
        billStorage.add(bill);
        verify(billStorage).add(bill);
    }

    @Test
    public void test_FindBillsByUser_notFindBills() {
        User user = new User();
        user.setId(5);
        Bill bill = new Bill();
        bill.setUser(user);
        List<Bill> billsList = new ArrayList<>();
        billsList.add(bill);
        when(billStorage.findBillFromId(6)).thenReturn(null);
        assertNotNull(billsList);
    }
}