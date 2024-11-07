package com.example.demo.serviceTest.impl;

import com.example.demo.dto.request.BillRequest;
import com.example.demo.dto.response.BillResponse;
import com.example.demo.dto.response.PrintBillDto;
import com.example.demo.dto.response.PrintBillResponse;
import com.example.demo.exception.BalanceException;
import com.example.demo.exception.BillException;
import com.example.demo.exception.UserException;
import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.repository.BillRepository;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
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

import static com.example.demo.serviceTest.TestData.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BillServiceTest extends TestCase {

    @Autowired
    BillService subj;

    @MockBean
    BillRepository billRepository;
    @MockBean
    Converter<Object, Object> converter;
    @MockBean
    UserService userService;

    @Before
    public void setUp() {
    }

    @Test
    public void addBill_Ok() {
        User user = createUser();
        Bill bill = createBill(user);
        BillRequest billRequest = new BillRequest(user.getId(), bill.getBillName());

        String message = "Success";

        when(userService.findUserById(user.getId())).thenReturn(user);
        when(billRepository.save(any())).thenReturn(bill);
        BillResponse billResponseTest = subj.addBill(billRequest);

        assertEquals(billResponseTest.getMessage(), message);
        assertEquals(billResponseTest.getUserName(), user.getUserName());
        assertEquals(billResponseTest.getBillName(), bill.getBillName());
        assertEquals(billResponseTest.getBalance(), bill.getBalance());
    }

    @Test(expected = UserException.class)
    public void addBill_fail() {
        User user = createUser();
        Bill bill = createBill(user);
        BillRequest billRequest = new BillRequest(user.getId(), bill.getBillName());

        when(userService.findUserById(user.getId())).thenThrow(new UserException("Пользователь не найден"));
        subj.addBill(billRequest);
    }

    @Test
    public void findBillsByUser_Ok() {
        User user = createUser();
        Bill testBill1 = createBill(user);
        Bill testBill2 = createBill(user);

        List<Bill> billList = new ArrayList<>();
        billList.add(testBill1);
        billList.add(testBill2);

        PrintBillDto printBillDto1 = new PrintBillDto(user.getId(), testBill1.getBillName(), testBill1.getBalance());
        PrintBillDto printBillDto2 = new PrintBillDto(user.getId(), testBill2.getBillName(), testBill2.getBalance());

        List<PrintBillDto> printBillDtoList = new ArrayList<>();
        printBillDtoList.add(printBillDto1);
        printBillDtoList.add(printBillDto2);
        user.setListBills(billList);

        PrintBillResponse printBillResponse1 = new PrintBillResponse(user.getUserName(), printBillDtoList);

        when(userService.findUserById(user.getId())).thenReturn(user);
        PrintBillResponse printBillResponse = subj.findBillsByUser(user.getId());
        assertEquals(printBillResponse1.getUserName(), printBillResponse.getUserName());
        assertEquals(printBillResponse1.getPrintBillDtoList(), printBillResponse.getPrintBillDtoList());

    }

    @Test(expected = UserException.class)
    public void findBillsByUser_fail() {
        User user = createUser();

        when(userService.findUserById(user.getId())).thenThrow(new UserException("Пользователь не найден"));
        subj.findBillsByUser(user.getId());
    }

    @Test
    public void deleteBillOk() {
        User user = createUser();
        Bill bill = createBill(user);

        when(billRepository.findById(bill.getId())).thenReturn(Optional.of(bill));
        doNothing().when(billRepository).delete(bill);
    }

    @Test
    public void sumToBillTransfer_Ok() {
        Bill billOldBalance = createBillWithoutUser();
        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());
        Bill billNewBalance = createBillWithoutUser();

        when(billRepository.save(any())).thenReturn(billOldBalance);
        Bill returnTestBill = subj.sumToBillTransfer(billOldBalance, sumTransfer);
        assertEquals(returnTestBill.getBalance(), billNewBalance.getBalance());
    }

    @Test
    public void reduceFromBillTransfer_Ok() {
        Bill billOldBalance = createBillWithoutUser();
        BigDecimal sumTransfer = BigDecimal.valueOf(createIntRandom());
        Bill billNewBalance = createBillWithoutUser();

        when(billRepository.save(any())).thenReturn(billOldBalance);
        Bill returnTestBill = subj.reduceFromBillTransfer(billOldBalance, sumTransfer);
        assertEquals(returnTestBill.getBalance(), billNewBalance.getBalance());
    }

    @Test(expected = BalanceException.class)
    public void reduceFromBillTransfer_fail() {
        Bill billOldBalance = createBillWithoutUser();
        billOldBalance.setBalance(BigDecimal.valueOf(50));
        BigDecimal sumTransfer = BigDecimal.valueOf(100);

        subj.reduceFromBillTransfer(billOldBalance, sumTransfer);
    }

    @Test
    public void findBillById_Ok() {
        Bill bill = createBillWithoutUser();

        when(billRepository.findById(bill.getId())).thenReturn(Optional.of(bill));
        Bill returnBill = subj.findBillById(bill.getId());
        assertEquals(bill, returnBill);
    }

    @Test(expected = BillException.class)
    public void findBillById_fail() {
        Bill bill = createBillWithoutUser();

        when(billRepository.findById(bill.getId())).thenThrow(new BillException("Нет такого счета"));
        subj.findBillById(bill.getId());
    }
}
