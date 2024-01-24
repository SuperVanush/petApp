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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        User testUser = User.builder()
                .login("QQQ")
                .id(10)
                .build();

        Bill testUserFirstBill = Bill.builder()
                .user(testUser)
                .billName("secondUserFirstBill")
                .balance(BigDecimal.valueOf(150))
                .build();
        Bill testUserSecondBill = Bill.builder()
                .user(testUser)
                .billName("secondUserSecondBill")
                .balance(BigDecimal.valueOf(200))
                .build();

        List<Bill> testBillList = new ArrayList<>();
        testBillList.add(testUserFirstBill);
        testBillList.add(testUserSecondBill);

        BillRequest billRequest = BillRequest.builder()
                .login(testUser.getLogin())
                .build();

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

        List<BillDtoResponse> billDtoResponseList = new ArrayList<>();
        billDtoResponseList.add(billDtoResponseFirst);
        billDtoResponseList.add(billDtoResponseSecond);

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
        Bill testBill = Bill.builder()
                .billName("billName")
                .build();
        String billName = testBill.getBillName();
        when(billRepository.findBillByBillName(billName)).thenReturn((Optional.of(testBill)));

        Bill billFromBase = subj.findBillByName(billName);
        assertEquals(testBill, billFromBase);
    }
}