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
        User userForBillFromDatabase = new User();
        userForBillFromDatabase.setId(5);
        User userNotBillInDatabase = new User();
        userNotBillInDatabase.setId(2);
        Bill billInCollectionFromDatabase = new Bill();
        billInCollectionFromDatabase.setUser(userForBillFromDatabase);
        List<Bill> listBillsFromDatabase = new ArrayList<>();
        listBillsFromDatabase.add(billInCollectionFromDatabase);
        when(billStorage.getListOfElements()).thenReturn(listBillsFromDatabase);
        List<Bill> listNotInDatabase = subj.findBillsByUser(userNotBillInDatabase);
        assertEquals(listNotInDatabase.size(),0);
    }

    @Test
    public void test_FindBillsByUser_Ok() {
        User userForBillFromDatabase = new User();
        userForBillFromDatabase.setId(5);
        User userNotBillInDatabase = new User();
        userNotBillInDatabase.setId(5);
        Bill billInCollectionFromDatabase = new Bill();
        billInCollectionFromDatabase.setUser(userForBillFromDatabase);
        List<Bill> listBillsFromDatabase = new ArrayList<>();
        listBillsFromDatabase.add(billInCollectionFromDatabase);
        when(billStorage.getListOfElements()).thenReturn(listBillsFromDatabase);
        List<Bill> listNotInDatabase = subj.findBillsByUser(userNotBillInDatabase);
        assertEquals(listNotInDatabase,listBillsFromDatabase);
    }
}