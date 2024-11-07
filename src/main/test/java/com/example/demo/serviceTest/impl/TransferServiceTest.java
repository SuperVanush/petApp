package com.example.demo.serviceTest.impl;

import com.example.demo.dto.response.PrintTransferDto;
import com.example.demo.dto.response.PrintTransferResponse;
import com.example.demo.exception.BillException;
import com.example.demo.model.Bill;
import com.example.demo.model.Transfer;
import com.example.demo.model.User;
import com.example.demo.model.types.TypeBill;
import com.example.demo.repository.TransferRepository;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.TransferService;
import com.example.demo.serviceTest.TestData;
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

    @Test
    public void deleteTransfer_OK() {
        UUID id = createId();

        doNothing().when(transferRepository).deleteById(id);
    }

    @Test
    public void printTransfersByUser_FromBill_Ok() {
        User user = TestData.createUser();
        Bill bill = TestData.createBill(user);

        BigDecimal sumTransfer1 = BigDecimal.valueOf(456);
        BigDecimal sumTransfer2 = BigDecimal.valueOf(111);

        Transfer fromTransfer1 = TestData.createTransferBetweenBills(bill, user, sumTransfer1);
        Transfer fromTransfer2 = TestData.createTransferBetweenBills(bill, user, sumTransfer2);

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(fromTransfer1);
        transferList.add(fromTransfer2);

        PrintTransferDto printTransferDto1 = new PrintTransferDto();
        printTransferDto1.setFromUserName(user.getUserName());
        printTransferDto1.setFromBillName(bill.getBillName());
        printTransferDto1.setToUserName(user.getUserName());
        printTransferDto1.setToBillName(bill.getBillName());
        printTransferDto1.setSumTransfer(sumTransfer1);

        PrintTransferDto printTransferDto2 = new PrintTransferDto();
        printTransferDto1.setFromUserName(user.getUserName());
        printTransferDto1.setFromBillName(bill.getBillName());
        printTransferDto1.setToUserName(user.getUserName());
        printTransferDto1.setToBillName(bill.getBillName());
        printTransferDto1.setSumTransfer(sumTransfer2);

        List<PrintTransferDto> printTransferDtoList = new ArrayList<>();
        printTransferDtoList.add(printTransferDto1);
        printTransferDtoList.add(printTransferDto2);

        when(billService.findBillById(bill.getId())).thenReturn(bill);
        when(converter.convert(transferList)).thenReturn(printTransferDtoList);
        TypeBill typeFromBill = TypeBill.FROM_BILL;

        PrintTransferResponse printTransferResponse = new PrintTransferResponse();
        printTransferResponse.setUserName(user.getUserName());
        printTransferResponse.setBillName(bill.getBillName());
        printTransferResponse.setPrintTransferDtoList(printTransferDtoList);

        when(transferRepository.findTransfersByFromBill_id(bill.getId())).
                thenReturn(transferList);

        PrintTransferResponse printTransferResponseReturn = subj.printTransfersByUser(bill.getId(), typeFromBill);

        assertEquals(printTransferResponseReturn, printTransferResponseReturn);
    }

    @Test(expected = BillException.class)
    public void printTransfersByUser_FromBill_fail() {
        User user = TestData.createUser();
        Bill bill = TestData.createBill(user);
        TypeBill typeFromBill = TypeBill.FROM_BILL;

        when(billService.findBillById(bill.getId())).thenThrow(new BillException("Нет такого счета"));
        subj.printTransfersByUser(bill.getId(), typeFromBill);
    }

    @Test
    public void printTransfersByUser_ToBill_Ok() {
        User user = TestData.createUser();
        Bill bill = TestData.createBill(user);

        BigDecimal sumTransfer1 = BigDecimal.valueOf(456);
        BigDecimal sumTransfer2 = BigDecimal.valueOf(111);

        Transfer fromTransfer1 = TestData.createTransferBetweenBills(bill, user, sumTransfer1);
        Transfer fromTransfer2 = TestData.createTransferBetweenBills(bill, user, sumTransfer2);

        List<Transfer> transferList = new ArrayList<>();
        transferList.add(fromTransfer1);
        transferList.add(fromTransfer2);

        PrintTransferDto printTransferDto1 = new PrintTransferDto();
        printTransferDto1.setFromUserName(user.getUserName());
        printTransferDto1.setFromBillName(bill.getBillName());
        printTransferDto1.setToUserName(user.getUserName());
        printTransferDto1.setToBillName(bill.getBillName());
        printTransferDto1.setSumTransfer(sumTransfer1);

        PrintTransferDto printTransferDto2 = new PrintTransferDto();
        printTransferDto1.setFromUserName(user.getUserName());
        printTransferDto1.setFromBillName(bill.getBillName());
        printTransferDto1.setToUserName(user.getUserName());
        printTransferDto1.setToBillName(bill.getBillName());
        printTransferDto1.setSumTransfer(sumTransfer2);

        List<PrintTransferDto> printTransferDtoList = new ArrayList<>();
        printTransferDtoList.add(printTransferDto1);
        printTransferDtoList.add(printTransferDto2);

        when(billService.findBillById(bill.getId())).thenReturn(bill);
        when(converter.convert(transferList)).thenReturn(printTransferDtoList);
        TypeBill typeFromBill = TypeBill.TO_BILL;

        PrintTransferResponse printTransferResponse = new PrintTransferResponse();
        printTransferResponse.setUserName(user.getUserName());
        printTransferResponse.setBillName(bill.getBillName());
        printTransferResponse.setPrintTransferDtoList(printTransferDtoList);

        when(transferRepository.findTransfersByToBill_Id(bill.getId())).
                thenReturn(transferList);

        PrintTransferResponse printTransferResponseReturn = subj.printTransfersByUser(bill.getId(), typeFromBill);

        assertEquals(printTransferResponseReturn, printTransferResponseReturn);
    }

    @Test(expected = BillException.class)
    public void printTransfersByUser_ToBill_fail() {
        User user = TestData.createUser();
        Bill bill = TestData.createBill(user);
        TypeBill typeFromBill = TypeBill.FROM_BILL;

        when(billService.findBillById(bill.getId())).thenThrow(new BillException("Нет такого счета"));
        subj.printTransfersByUser(bill.getId(), typeFromBill);
    }

    @Test(expected = Exception.class)
    public void printTransfersByUser_ToBill_wrongType() {
        User user = TestData.createUser();
        Bill bill = TestData.createBill(user);

        when(billService.findBillById(bill.getId())).thenReturn(bill);

        TypeBill typeFromBill = TypeBill.valueOf("Error");

        subj.printTransfersByUser(bill.getId(), typeFromBill);
    }
}

