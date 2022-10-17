package com.example.demo.dao.impl;

import com.example.demo.factory.Factory;
import junit.framework.TestCase;
import org.junit.Before;

public class BillStorageTest extends TestCase {

    BillStorageTest subj;

@Before
public void setUp () throws Exception{
        System.setProperty("jdbcUrl","jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName","sa");
        System.setProperty("jdbcPassword","");
        subj = (BillStorageTest) Factory.getBillStorageInstance();
    }
    public void testAdd() {

    }

    public void testGetListOfElements() {
    }

    public void testFindBillFromId() {
    }

    public void testUpdateBill() {
    }
}