package com.example.demo.dao.impl;

import com.example.demo.model.Transfer;
import com.example.demo.view.ViewConfig;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransferStorageTest extends TestCase {
    TransferStorage subj;
    UserStorage userStorage;
    BillStorage billStorage;

    @Before
    public void setUp() throws Exception {
        System.setProperty("jdbcUrl", "jdbc:h2:mem:testDatabase");
        System.setProperty("jdbcUserName", "sa");
        System.setProperty("jdbcPassword", "");
        ApplicationContext context = new AnnotationConfigApplicationContext(ViewConfig.class);
        subj = context.getBean(TransferStorage.class);
        userStorage = context.getBean(UserStorage.class);
        billStorage = context.getBean(BillStorage.class);
    }

    @Test
    public void testAddTransfer() {
        Transfer transfer = new Transfer();
        transfer.setId(2);
        transfer.setIdFromUser(20);
        transfer.setIdFromBill(2);
        transfer.setIdToUser(15);
        transfer.setIdToBill(3);
        transfer.setSumTransaction(BigDecimal.valueOf(100));
        transfer.setTimeDateTransaction(LocalDateTime.now());

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(transfer);

        subj.add(transfer);
        List<Transfer> returnTransferList = subj.getListOfElements();
        assertEquals(transferList.size(), returnTransferList.size());
    }

    @Test
    public void testGetListOfElements() {
        Transfer firstTransfer = new Transfer();
        firstTransfer.setId(2);
        firstTransfer.setIdFromUser(20);
        firstTransfer.setIdFromBill(2);
        firstTransfer.setIdToUser(15);
        firstTransfer.setIdToBill(3);
        firstTransfer.setSumTransaction(BigDecimal.valueOf(100));
        firstTransfer.setTimeDateTransaction(LocalDateTime.now());

        Transfer secondTransfer = new Transfer();
        secondTransfer.setId(3);
        secondTransfer.setIdFromUser(2);
        secondTransfer.setIdFromBill(3);
        secondTransfer.setIdToUser(5);
        secondTransfer.setIdToBill(1);
        secondTransfer.setSumTransaction(BigDecimal.valueOf(500));
        secondTransfer.setTimeDateTransaction(LocalDateTime.now());

        Transfer thirdTransfer = new Transfer();
        thirdTransfer.setId(4);
        thirdTransfer.setIdFromUser(11);
        thirdTransfer.setIdFromBill(1);
        thirdTransfer.setIdToUser(2);
        thirdTransfer.setIdToBill(3);
        thirdTransfer.setSumTransaction(BigDecimal.valueOf(400));
        thirdTransfer.setTimeDateTransaction(LocalDateTime.now());

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(firstTransfer);
        transferList.add(secondTransfer);
        transferList.add(thirdTransfer);

        subj.add(firstTransfer);
        subj.add(secondTransfer);
        subj.add(thirdTransfer);

        List<Transfer> returnTransferList = subj.getListOfElements();
        assertEquals(transferList.size(), returnTransferList.size());
    }
}
