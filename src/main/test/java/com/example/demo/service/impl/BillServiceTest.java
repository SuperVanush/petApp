package com.example.demo.service.impl;


import com.example.demo.model.Bill;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.repository.BillRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.converter.iml.BillConverter;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestData.*;
import static org.mockito.ArgumentMatchers.any;
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
    BillConverter billConverter;

    @Before
    public void setUp() {
    }

    @Test
    public void test_AddBill_Ok() {
        User user = createUser();
        BillRequest billRequest = createBillRequest(user);
        Bill bill = Bill.builder()
                .billName(billRequest.getBillName())
                .balance(billRequest.getBalance())
                .user(user)
                .build();
        List<Bill> billList = new ArrayList<>();
        billList.add(bill);
        user.setBills(billList);

        when(userRepository.findByLogin(billRequest.getLogin())).thenReturn(Optional.of(user));
        when(billRepository.save(any())).thenReturn(bill);
        when(billRepository.findAll()).thenReturn(billList);

        BillResponse response = subj.addBill(billRequest);

        String loginUserFromSubj = response.getLogin();
        assertEquals(loginUserFromSubj, bill.getUser().getLogin());
        assertEquals(response.getMessage(), "Success");
        assertEquals(response.getBillList(), billList);
    }

    @Test
    public void test_FindBillsByUser_notFindBills() {
        User firstUser = createUser();
        Bill billForFirstUser = createBillWithUser(firstUser);
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
        User testUser = createUser();
        Bill testUserFirstBill = createBillWithUser(testUser);
        Bill testUserSecondBill = createBillWithUser(testUser);
        List<Bill> testBillList = List.of(testUserFirstBill, testUserSecondBill);
        BillRequest billRequest = createBillRequest(testUser);
        BillDtoResponse billDtoResponseFirst = BillDtoResponse.builder()
                .userId(testUser.getId())
                .billName(testUserFirstBill.getBillName())
                .balance(testUserFirstBill.getBalance())
                .build();
        BillDtoResponse billDtoResponseSecond = BillDtoResponse.builder()
                .userId(testUser.getId())
                .billName(testUserSecondBill.getBillName())
                .balance(testUserSecondBill.getBalance())
                .build();
        List<BillDtoResponse> billDtoResponseList = List.of(billDtoResponseFirst, billDtoResponseSecond);
        BillResponse standardBillResponse = BillResponse.builder()
                .message("Success")
                .login(testUser.getLogin())
                .billList(billDtoResponseList)
                .build();

        when(userRepository.findByLogin(testUser.getLogin())).thenReturn(Optional.of(testUser));
        when(billRepository.findAll()).thenReturn(testBillList);
        when(billConverter.convert(testUserFirstBill)).thenReturn(billDtoResponseFirst);
        when(billConverter.convert(testUserSecondBill)).thenReturn(billDtoResponseSecond);
        BillResponse billResponseTest = subj.findBillsByUser(billRequest);
        assertEquals(standardBillResponse, billResponseTest);
    }

    @Test
    public void test_FindBillsByName_Ok() {
        Bill testBill = createBill();
        String billName = testBill.getBillName();

        when(billRepository.findBillByBillName(billName)).thenReturn((Optional.of(testBill)));
        Bill billFromBase = subj.findBillByName(billName);
        assertEquals(testBill, billFromBase);
    }
}