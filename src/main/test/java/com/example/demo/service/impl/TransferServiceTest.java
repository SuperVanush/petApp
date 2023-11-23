package com.example.demo.service.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransferServiceTest extends TestCase {

    TransferRepository transferRepository;
    TransferService subj;
    BillRepository billRepository;

    @Before
    public void setUp() {
        billRepository = mock(BillRepository.class);
        transferRepository = mock(TransferRepository.class);
        subj = new TransferService(transferRepository, billRepository);
    }

    @Test
    public void test_addTransaction_Ok() {
        Transfer transfer = Transfer.builder()
                .idFromUser(1)
                .idFromBill(1)
                .idToUser(2)
                .idToBill(2)
                .sumTransaction(BigDecimal.valueOf(500))
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);
        verify(transferRepository).save(transfer);
    }

    @Test
    public void test_findTransferByBillsId_ok() {
        Transfer firstTransfer = Transfer.builder().idFromBill(5).build();

        Transfer secondTransfer = Transfer.builder().idFromBill(11).build();

        List<Transfer> listTransfer = new ArrayList<>();
        listTransfer.add(firstTransfer);
        listTransfer.add(secondTransfer);

        List<Transfer> listTransferForCompare = new ArrayList<>();
        listTransferForCompare.add(secondTransfer);

        when(transferRepository.findAll()).thenReturn(listTransfer);
        List<Transfer> transferListForElevenBill = subj.findTransferByBillsId(11);
        assertEquals(transferListForElevenBill, listTransferForCompare);
    }

    @Test
    public void test_findTransferByBillsId_not_find_transfer() {
        Bill firstBill = Bill.builder().id(6).build();

        Transfer firstTransfer = Transfer.builder().idFromBill(firstBill.getId()).build();

        Bill secondBill = Bill.builder().id(2).build();

        List<Transfer> firstTransferList = new ArrayList<>();
        firstTransferList.add(firstTransfer);
        when(transferRepository.findAll()).thenReturn(firstTransferList);

        List<Transfer> secondTransferList = subj.findTransferByBillsId(secondBill.getId());
        assertEquals(secondTransferList.size(), 0);

    }

    @Test
    public void test_sumBalanceTransaction_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(6)).id(2).build();
        BigDecimal sumDigit = BigDecimal.valueOf(3);
        when(billRepository.findBillById(6).get()).thenReturn(bill);
        Bill returnBill = subj.sumBalanceTransaction(6, BigDecimal.valueOf(3));
        verify(billRepository).save(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

    @Test
    public void test_reduceBalance_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(9)).id(2).build();
        BigDecimal reduceBalance = BigDecimal.valueOf(2);
        when(billRepository.findBillById(2).get()).thenReturn(bill);
        Bill returnBill = subj.reduceBalance(2, reduceBalance);
        verify(billRepository).save(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }
}