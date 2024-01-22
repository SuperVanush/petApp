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
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BillServiceTest extends TestCase {

    @Autowired
    BillService subj;
    @MockBean
    UserService userService;
    @MockBean
    BillRepository billRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    Converter<Bill, BillDtoResponse> converter;

    @Before
    public void setUp() {
    }

    @Test
    public void test_AddBill_Ok() {
        BillRequest billRequest = BillRequest.builder()
                .login("qqq")
                .billName("NewBill")
                .balance(BigDecimal.valueOf(111))
                .build();

        Bill oldBill = Bill.builder()
                .billName("oldBill")
                .balance(BigDecimal.valueOf(123))
                .build();

        List<Bill> billList = new ArrayList<>();
        billList.add(oldBill);

        Bill billFromRequest = Bill.builder()
                .billName(billRequest.getBillName())
                .balance(BigDecimal.valueOf(111))
                .build();

        User userFromDataBase = User.builder()
                .login("qqq")
                .id(1)
                .bills(billList)
                .build();

        Bill newBill = Bill.builder()
                .id(5)
                .billName(billFromRequest.getBillName())
                .balance(billFromRequest.getBalance())
                .user(userFromDataBase)
                .build();

        when(userRepository.findByLogin(billRequest.getLogin())).thenReturn(Optional.of(userFromDataBase));
        when(billRepository.save(billFromRequest)).thenReturn(newBill);
        assertEquals(userFromDataBase.getId(), newBill.getUser().getId());
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
        assertNull(listSecondUser);
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

        BillDtoResponse billDtoResponse = BillDtoResponse.builder()
                .userId(2)
                .build();

        List<BillDtoResponse> billDtoResponses = new ArrayList<>();
        billDtoResponses.add(billDtoResponse);

        when(billRepository.findAll().stream().filter(bill -> secondUser.equals(billForSecondUser.getUser())).map(converter::convert)
                .collect(Collectors.toList())).thenReturn(billDtoResponses);

        BillResponse billResponse = subj.findBillsByUser(request);
        assertEquals(billResponse, billDtoResponses);
    }
}