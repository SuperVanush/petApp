package com.example.demo.service.impl;


import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.converter.Converter;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BillServiceTest extends TestCase {

    BillService subj;
    UserService userService;
    BillRepository billRepository;
    UserRepository userRepository;
    Converter<Bill, BillDtoResponse> converter;

    @Before
    public void setUp() {
        billRepository = mock(BillRepository.class);
        userService = mock(UserService.class);
        converter = mock(Converter.class);
        subj = new BillService(billRepository,userRepository, converter);
    }

    @Test
    public void test_AddBill_Ok() {
        Bill bill = Bill.builder()
                .billName("qqq").balance(BigDecimal.valueOf(123)).build();
        billRepository.save(bill);
        verify(billRepository).save(bill);
    }

    @Test
    public void test_FindBillsByUser_notFindBills() {
        User firstUser = User.builder().login("LLL").build();
        Bill billForFirstUser = Bill.builder().user(firstUser).build();

        BillRequest request = BillRequest.builder().login("NNN").build();

        List<Bill> listBillFirstUser = new ArrayList<>();
        listBillFirstUser.add(billForFirstUser);
        when(billRepository.findAll()).thenReturn(listBillFirstUser);

        BillResponse billResponse = subj.findBillsByUser(request);
        List<BillDtoResponse> listSecondUser = billResponse.getBillList();
        assertEquals(listSecondUser.size(), 0);
    }

    @Test
    public void test_FindBillsByUser_Ok() {
        User firstUser = User.builder().id(5).build();
        Bill billForFirstUser = Bill.builder().user(firstUser).build();

        User secondUser = User.builder().id(2).build();
        Bill billForSecondUser = Bill.builder().user(secondUser).build();

        BillRequest request = BillRequest.builder().login("NNN").build();

        List<Bill> listBillsFromDatabase = new ArrayList<>();
        listBillsFromDatabase.add(billForFirstUser);
        listBillsFromDatabase.add(billForSecondUser);

        List<Bill> listForComparison = new ArrayList<>();
        listForComparison.add(billForSecondUser);

        when(billRepository.findAll()).thenReturn(listBillsFromDatabase);
        BillResponse billResponse = subj.findBillsByUser(request);
        List<BillDtoResponse> listBillsSecondUser = billResponse.getBillList();
        assertEquals(listBillsSecondUser, listForComparison);
    }
}