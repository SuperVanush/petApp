package com.example.demo.service.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
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
    BillService billService;
    UserService userService;

    @Before
    public void setUp() {
        billRepository = mock(BillRepository.class);
        transferRepository = mock(TransferRepository.class);
        subj = new TransferService(transferRepository, billRepository, billService, userService);
    }

    @Test
    public void test_addTransaction_Ok() {
        User user1 = new User();
        User user2 = new User();
        Bill billUser1 = new Bill();
        Bill billUser2 = new Bill();
        Transfer transfer = Transfer.builder()
                .fromUser(user1)
                .fromBill(billUser1)
                .toUser(user2)
                .toBill(billUser2)
                .sumTransaction(BigDecimal.valueOf(500))
                .timeDateTransaction(new Timestamp(System.currentTimeMillis()))
                .build();
        transferRepository.save(transfer);
        verify(transferRepository).save(transfer);
    }

    @Test
    public void test_findTransferByBillsName_ok() {

        Bill firstBill = Bill.builder()
                .id(5)
                .billName("VTB")
                .build();

        Bill secondBill = Bill.builder()
                .id(11)
                .billName("ALFA")
                .build();

        Transfer firstTransfer = Transfer.builder().fromBill(firstBill).build();

        Transfer secondTransfer = Transfer.builder().toBill(secondBill).build();

        List<Transfer> listTransfer = new ArrayList<>();
        listTransfer.add(firstTransfer);
        listTransfer.add(secondTransfer);

        List<Transfer> listTransferForCompare = new ArrayList<>();
        listTransferForCompare.add(secondTransfer);

        TransferRequest request = TransferRequest.builder().nameFromBill("VTB").build();

        when(transferRepository.findAll()).thenReturn(listTransfer);
        PrintTransferResponse printTransferResponse = subj.findTransferByBillsName(request);
        List<Transfer> transferList = printTransferResponse.getTransferList();
        assertEquals(transferList, listTransferForCompare);
    }

    @Test
    public void test_findTransferByBillsName_not_find_transfer() {
        Bill firstBill = Bill.builder()
                .id(6).build();

        Transfer firstTransfer = Transfer.builder().fromBill(firstBill).build();

        Bill secondBill = Bill.builder().id(2).build();

        List<Transfer> firstTransferList = new ArrayList<>();
        firstTransferList.add(firstTransfer);
        when(transferRepository.findAll()).thenReturn(firstTransferList);

        TransferRequest request = TransferRequest.builder().nameFromBill("VTB").build();

        PrintTransferResponse printTransferResponse = subj.findTransferByBillsName(request);
        List<Transfer> transferList = printTransferResponse.getTransferList();

        assertEquals(transferList.size(), 0);

    }

    @Test
    public void test_sumBalanceTransaction_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(6)).id(2).build();
        BigDecimal sumDigit = BigDecimal.valueOf(3);
        when(billRepository.findBillByBillName("ALFA").get()).thenReturn(bill);
        Transfer transfer = subj.sumBalanceTransaction("ALFA", sumDigit);
        Bill returnBill = transfer.getToBill();

        verify(billRepository).save(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

    @Test
    public void test_reduceBalance_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(9)).id(2).build();
        BigDecimal reduceBalance = BigDecimal.valueOf(2);
        when(billRepository.findBillByBillName("VTB").get()).thenReturn(bill);
        Transfer transfer = subj.reduceBalance("VTB", reduceBalance);
        Bill returnBill = transfer.getFromBill();

        verify(billRepository).save(returnBill);
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }
}