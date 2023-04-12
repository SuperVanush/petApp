package com.example.demo.service.impl;

import com.example.demo.dao.impl.BillStorage;
import com.example.demo.dao.impl.TransferStorage;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransferServiceTest extends TestCase {

    TransferStorage transferStorage;
    TransferService subj;
    BillStorage billStorage;

    @Before
    public void setUp() throws Exception {
        billStorage = mock(BillStorage.class);
        transferStorage = mock(TransferStorage.class);
        subj = new TransferService(transferStorage, billStorage);
    }

    @Test
    public void test_addTransaction_Ok() {
        Transfer transfer = new Transfer();
        transfer.setIdFromUser(1);
        transfer.setIdFromBill(1);
        transfer.setIdToUser(2);
        transfer.setIdToBill(2);
        transfer.setSumTransaction(BigDecimal.valueOf(500));
        transferStorage.add(transfer);
        verify(transferStorage).add(transfer);
    }

    @Test
    public void test_findTransferByBillsId_ok() {
        Transfer firstTransfer = new Transfer();
        firstTransfer.setIdFromBill(5);

        Transfer secondTransfer = new Transfer();
        secondTransfer.setIdFromBill(11);

        List<Transfer> listTransfer = new ArrayList<>();
        listTransfer.add(firstTransfer);
        listTransfer.add(secondTransfer);

        List<Transfer> listTransferForCompare = new ArrayList<>();
        listTransferForCompare.add(secondTransfer);

        when(transferStorage.getListOfElements()).thenReturn(listTransfer);
        List<Transfer> transferListForElevenBill = subj.findTransferByBillsId(11);
        assertEquals(transferListForElevenBill, listTransferForCompare);
    }

    @Test
    public void test_findTransferByBillsId_not_find_transfer() {
        Bill firstBill = new Bill();
        firstBill.setId(6);
        Transfer firstTransfer = new Transfer();
        firstTransfer.setIdFromBill(firstBill.getId());

        Bill secondBill = new Bill();
        secondBill.setId(2);

        List<Transfer> firstTransferList = new ArrayList<>();
        firstTransferList.add(firstTransfer);
        when(transferStorage.getListOfElements()).thenReturn(firstTransferList);

        List<Transfer> secondTransferList = subj.findTransferByBillsId(secondBill.getId());
        assertEquals(secondTransferList.size(), 0);

    }

    @Test
    public void test_sumBalanceTransaction_Ok() {
        Bill bill = new Bill();
        bill.setBalance(BigDecimal.valueOf(6));
        bill.setId(2);
        BigDecimal sumDigit = BigDecimal.valueOf(3);
        when(billStorage.findBillFromId(6)).thenReturn(bill);
        Bill returnBill = subj.sumBalanceTransaction(6, BigDecimal.valueOf(3));
        verify(billStorage).updateBill(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

    @Test
    public void test_reduceBalance_Ok() {
        Bill bill = new Bill();
        bill.setBalance(BigDecimal.valueOf(9));
        bill.setId(2);
        BigDecimal reduceBalance = BigDecimal.valueOf(2);
        when(billStorage.findBillFromId(2)).thenReturn(bill);
        Bill returnBill = subj.reduceBalance(2, reduceBalance);
        verify(billStorage).updateBill(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

}
