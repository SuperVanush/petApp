package com.example.demo.serviceTest.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.demo.serviceTest.TestData.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)

public class TransferServiceTest extends TestCase {

    @Autowired
    TransferService subj;

    @MockBean
    TransferRepository transferRepository;
    @MockBean
    BillService billService;
    @MockBean
    Converter<Object, Object> converter;

    @Before
    public void setUp() {
    }

    @Test
    public void addTransfer_OK() {
        User fromUser = createUser();
        Bill fromBill = createBill(fromUser);

        User toUser = createUser();
        Bill toBill = createBill(toUser);

        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());

        Transfer transfer = createTransferBetweenUsers(fromBill, fromUser, toBill, toUser, sumTransfer);

        when(transferRepository.save(any())).thenReturn(transfer);
        Transfer testTransfer = subj.addTransfer(fromBill, toBill, sumTransfer);
        assertEquals(testTransfer.getId(), transfer.getId());
        assertEquals(testTransfer.getFromBill(), transfer.getFromBill());
        assertEquals(testTransfer.getToBill(), transfer.getToBill());
        assertEquals(testTransfer.getFromUser(), transfer.getFromUser());
        assertEquals(testTransfer.getToUser(), transfer.getToUser());
        assertEquals(testTransfer.getSumTransfer(), transfer.getSumTransfer());
    }

    @Test
    public void deleteTransfer_OK() {
        UUID id = createId();

        doNothing().when(transferRepository).deleteById(id);
    }

    @Test
    public void depositOnBill_ok() {
        User toUser = createUser();
        Bill toBill = createBill(toUser);

        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());

        Transfer transfer = createTransferBetweenBills(toBill, toUser, sumTransfer);

        when(billService.sumToBillTransfer(toBill, sumTransfer)).thenReturn(toBill);
        when(transferRepository.save(any())).thenReturn(transfer);
        Transfer testTransfer = subj.addTransfer(toBill, toBill, sumTransfer);
        assertEquals(testTransfer.getId(), transfer.getId());
        assertEquals(testTransfer.getFromBill(), transfer.getFromBill());
        assertEquals(testTransfer.getToBill(), transfer.getToBill());
        assertEquals(testTransfer.getFromUser(), transfer.getFromUser());
        assertEquals(testTransfer.getToUser(), transfer.getToUser());
        assertEquals(testTransfer.getSumTransfer(), transfer.getSumTransfer());
    }

    @Test
    public void withdrawFromBill_ok() {
        User fromUser = createUser();
        Bill fromBill = createBill(fromUser);

        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());

        Transfer transfer = createTransferBetweenBills(fromBill, fromUser, sumTransfer);

        when(billService.reduceFromBillTransfer(fromBill, sumTransfer)).thenReturn(fromBill);
        when(transferRepository.save(any())).thenReturn(transfer);

        Transfer testTransfer = subj.addTransfer(fromBill, fromBill, sumTransfer);
        assertEquals(testTransfer.getId(), transfer.getId());
        assertEquals(testTransfer.getFromBill(), transfer.getFromBill());
        assertEquals(testTransfer.getToBill(), transfer.getToBill());
        assertEquals(testTransfer.getFromUser(), transfer.getFromUser());
        assertEquals(testTransfer.getToUser(), transfer.getToUser());
        assertEquals(testTransfer.getSumTransfer(), transfer.getSumTransfer());
    }

    @Test
    public void transferBetweenUsers_OK() {
        User toUser = createUser();
        Bill toBill = createBill(toUser);

        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());

        User fromUser = createUser();
        Bill fromBill = createBill(fromUser);

        Transfer transfer = createTransferBetweenUsers(fromBill, fromUser, toBill, toUser, sumTransfer);

        when(billService.sumToBillTransfer(toBill, sumTransfer)).thenReturn(toBill);
        when(billService.reduceFromBillTransfer(fromBill, sumTransfer)).thenReturn(fromBill);
        when(transferRepository.save(any())).thenReturn(transfer);

        Transfer testTransfer = subj.addTransfer(fromBill, fromBill, sumTransfer);
        assertEquals(testTransfer.getId(), transfer.getId());
        assertEquals(testTransfer.getFromBill(), transfer.getFromBill());
        assertEquals(testTransfer.getToBill(), transfer.getToBill());
        assertEquals(testTransfer.getFromUser(), transfer.getFromUser());
        assertEquals(testTransfer.getToUser(), transfer.getToUser());
        assertEquals(testTransfer.getSumTransfer(), transfer.getSumTransfer());
    }
}
