package com.example.demo.service.impl;

import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.PrintTransferRequest;
import com.example.demo.model.dto.request.TransferRequest;
import com.example.demo.model.dto.response.PrintTransferResponse;
import com.example.demo.model.dto.response.TransferResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.TransferRepository;
import com.example.demo.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestData.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TransferServiceTest extends TestCase {

    @Autowired
    TransferService subj;
    @MockBean
    TransferRepository transferRepository;
    @MockBean
    BillRepository billRepository;
    @MockBean
    BillService billService;
    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
    }

    @Test
    public void test_addTransaction_Ok() {
        User fromUser = createUser();
        User toUser = createUser();
        Bill fromBill = createBillWithUser(fromUser);
        String nameFromBill = fromBill.getBillName();
        Bill toBill = createBillWithUser(toUser);
        String nameToBill = toBill.getBillName();
        BigDecimal sumTransaction = BigDecimal.valueOf(randomInt());
        Transfer transfer = createTransfer(fromUser, fromBill, toUser, toBill, sumTransaction);
        Transfer transferFromTest = createTransfer(fromUser, fromBill, toUser, toBill, sumTransaction);

        when(billService.findBillByName(nameFromBill)).thenReturn(fromBill);
        when(billService.findBillByName(nameToBill)).thenReturn(toBill);
        when(transferRepository.save(any())).thenReturn(transferFromTest);

        Transfer transferForTest = subj.addTransfer(fromUser, toUser, nameFromBill, nameToBill, sumTransaction);
        assertEquals(transfer.getFromBill().getBillName(), transferForTest.getFromBill().getBillName());
    }

    @Test
    public void test_findTransferByBillsName_ok() {
        Bill firstBill = createBill();
        Bill secondBill = createBill();
        Bill thirdBill = createBill();
        Transfer firstTransfer = createTransferByBills(firstBill, secondBill);
        Transfer secondTransfer = createTransferByBills(thirdBill, secondBill);
        Transfer thirdTransfer = createTransferByBills(firstBill, thirdBill);

        List<Transfer> listTransfer = new ArrayList<>();
        listTransfer.add(firstTransfer);
        listTransfer.add(secondTransfer);
        listTransfer.add(thirdTransfer);
        List<Transfer> listTransferForCompare = new ArrayList<>();
        listTransferForCompare.add(firstTransfer);
        listTransferForCompare.add(thirdTransfer);

        PrintTransferRequest request= PrintTransferRequest.builder()
                .billName(firstBill.getBillName())
                .build();
        when(billService.findBillByName(firstBill.getBillName())).thenReturn(firstBill);
        when(transferRepository.findAll()).thenReturn(listTransfer);
        PrintTransferResponse printTransferResponse = subj.findTransferByBillsName(request);
        List<Transfer> transferList = printTransferResponse.getTransferList();
        assertEquals(transferList, listTransferForCompare);
    }

    @Test
    public void test_findTransferByBillsName_not_find_transfer() {
        Bill fromBill = createBill();
        Bill toBill = createBill();
        Bill testBill = createBill();
        Transfer firstTransfer = createTransferByBills(fromBill, toBill);

        List<Transfer> firstTransferList = new ArrayList<>();
        firstTransferList.add(firstTransfer);
       PrintTransferRequest request =PrintTransferRequest.builder()
                .billName(testBill.getBillName())
                .build();

        when(transferRepository.findAll()).thenReturn(firstTransferList);
        when(billService.findBillByName(testBill.getBillName())).thenReturn(testBill);
        PrintTransferResponse printTransferResponse = subj.findTransferByBillsName(request);
        List<Transfer> transferList = printTransferResponse.getTransferList();
        assertNotSame(firstTransferList, transferList);
    }

    @Test
    public void test_sumBalanceTransaction_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(6)).id(2).build();
        BigDecimal sumDigit = BigDecimal.valueOf(3);

        when(billRepository.findBillByBillName("ALFA")).thenReturn(Optional.of(bill));
        when(billRepository.save(bill)).thenReturn(bill);
        Transfer transfer = subj.sumBalanceTransaction("ALFA", sumDigit);
        Bill returnBill = transfer.getToBill();
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

    @Test
    public void test_reduceBalance_Ok() {
        Bill bill = Bill.builder().balance(BigDecimal.valueOf(9)).id(2).build();
        BigDecimal reduceBalance = BigDecimal.valueOf(2);

        when(billRepository.save(bill)).thenReturn(bill);
        when(billRepository.findBillByBillName("VTB")).thenReturn(Optional.of(bill));
        Transfer transfer = subj.reduceBalance("VTB", reduceBalance);
        Bill returnBill = transfer.getFromBill();
        assertEquals(bill.getBalance(), returnBill.getBalance());
    }

    @Test
    public void test_transaction_between_bills() {
        BigDecimal transactionSum = BigDecimal.valueOf(randomInt());
        User testFromUser = createUser();
        User testToUser = createUser();
        Bill testFromBill = createBill();
        Bill testToBill = createBill();
        String loginFromUser = testFromUser.getLogin();
        String loginToUser = testToUser.getLogin();
        String nameFromBill = testFromBill.getBillName();
        String nameToBill = testToBill.getBillName();
        Transfer transfer = createTransfer(testFromUser, testFromBill, testToUser, testToBill, transactionSum);
        transfer.setId(0);

        TransferRequest transferRequest = createTransferRequest(loginFromUser, nameFromBill, loginToUser, nameToBill, transactionSum);
        String message = "Success";

        when(userRepository.findByLogin(loginFromUser)).thenReturn(Optional.of(testFromUser));
        when(userRepository.findByLogin(loginToUser)).thenReturn(Optional.of(testToUser));
        when(billRepository.findBillByBillName(nameFromBill)).thenReturn(Optional.of(testFromBill));
        when(billRepository.findBillByBillName(nameToBill)).thenReturn(Optional.of(testToBill));
        when(transferRepository.save(transfer)).thenReturn(transfer);
        TransferResponse transferResponse = subj.transactionBetweenBill(transferRequest);
        assertEquals(transferResponse.getMessage(), message);
        assertEquals(transferResponse.getIdTransaction(), transfer.getId());
    }
}