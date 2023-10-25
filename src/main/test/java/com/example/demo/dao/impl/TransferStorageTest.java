
package com.example.demo.dao.impl;

import com.example.demo.model.Transfer;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
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
        ApplicationContext context = new AnnotationConfigApplicationContext();
        subj = context.getBean(TransferStorage.class);
        userStorage = context.getBean(UserStorage.class);
        billStorage = context.getBean(BillStorage.class);
    }

    @Test
    public void testAddTransfer() {
        Transfer transfer = Transfer.builder()
                .id(2).idFromUser(20).idFromBill(2)
                .idToUser(15).idToBill(3)
                .sumTransaction(BigDecimal.valueOf(100))
                .timeDateTransaction(Timestamp.valueOf(LocalDateTime.now())).build();

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(transfer);

        subj.add(transfer);
        List<Transfer> returnTransferList = subj.getListOfElements();
        assertEquals(transferList.size(), returnTransferList.size());
    }

    @Test
    public void testGetListOfElements() {
        Transfer firstTransfer = Transfer.builder()
                .id(2).idFromUser(20).idFromBill(2)
                .idToUser(15).idToBill(3)
                .sumTransaction(BigDecimal.valueOf(100))
                .timeDateTransaction(Timestamp.valueOf(LocalDateTime.now())).build();

        Transfer secondTransfer = Transfer.builder()
                .id(3).idFromUser(2).idFromBill(3)
                .idToUser(5).idToBill(1)
                .sumTransaction(BigDecimal.valueOf(500))
                .timeDateTransaction(Timestamp.valueOf(LocalDateTime.now())).build();

        Transfer thirdTransfer = Transfer.builder()
                .id(4).idFromUser(11).idFromBill(1)
                .idToUser(2).idToBill(3)
                .sumTransaction(BigDecimal.valueOf(400))
                .timeDateTransaction(Timestamp.valueOf(LocalDateTime.now())).build();

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