package com.example.demo.service.impl;


import com.example.demo.dao.impl.BillStorage;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

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
        Bill bill = Bill.builder()
                .billName("qqq").balance(BigDecimal.valueOf(123)).build();
        billStorage.add(bill);
        verify(billStorage).add(bill);
    }

    @Test
    public void test_FindBillsByUser_notFindBills() {
        User firstUser = User.builder().id(5).build();
        Bill billForFirstUser = Bill.builder().user(firstUser).build();

        User secondUser = User.builder().id(2).build();

        List<Bill> listBillFirstUser = new ArrayList<>();
        listBillFirstUser.add(billForFirstUser);
        when(billStorage.getListOfElements()).thenReturn(listBillFirstUser);

        List<Bill> listSecondUser = subj.findBillsByUser(secondUser);
        assertEquals(listSecondUser.size(), 0);
    }

    @Test
    public void test_FindBillsByUser_Ok() {
        User firstUser = User.builder().id(5).build();
        Bill billForFirstUser = Bill.builder().user(firstUser).build();

        User secondUser = User.builder().id(2).build();
        Bill billForSecondUser = Bill.builder().user(secondUser).build();

        List<Bill> listBillsFromDatabase = new ArrayList<>();
        listBillsFromDatabase.add(billForFirstUser);
        listBillsFromDatabase.add(billForSecondUser);

        List<Bill> listForComparison = new ArrayList<>();
        listForComparison.add(billForSecondUser);

        when(billStorage.getListOfElements()).thenReturn(listBillsFromDatabase);
        List<Bill> listBillsSecondUser = subj.findBillsByUser(secondUser);
        assertEquals(listBillsSecondUser, listForComparison);
    }
}