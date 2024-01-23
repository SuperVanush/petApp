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
        User secondUser = User.builder()
                .login("QQQ")
                .id(10)
                .build();

        Bill firstUserFirstBill = Bill.builder()
                .user(secondUser)
                .billName("secondUserFirstBill")
                .balance(BigDecimal.valueOf(150))
                .build();
        Bill firstUserSecondBill = Bill.builder()
                .user(secondUser)
                .billName("secondUserSecondBill")
                .balance(BigDecimal.valueOf(200))
                .build();

        Bill secondUserFirstBill = Bill.builder()
                .user(secondUser)
                .billName("secondUserFirstBill")
                .balance(BigDecimal.valueOf(150))
                .build();
        Bill secondUserSecondBill = Bill.builder()
                .user(secondUser)
                .billName("secondUserSecondBill")
                .balance(BigDecimal.valueOf(200))
                .build();

        List<Bill> fullBillList = new ArrayList<>();
        fullBillList.add(firstUserFirstBill);
        fullBillList.add(firstUserSecondBill);
        fullBillList.add(secondUserFirstBill);
        fullBillList.add(secondUserSecondBill);

        BillRequest billRequest = BillRequest.builder()
                .login(secondUser.getLogin())
                .build();

        BillDtoResponse billDtoResponseFirst = BillDtoResponse.builder()
                .userId(secondUser.getId())
                .billName(secondUserFirstBill.getBillName())
                .balance(secondUserFirstBill.getBalance())
                .build();

        BillDtoResponse billDtoResponseSecond = BillDtoResponse.builder()
                .userId(secondUser.getId())
                .billName(secondUserSecondBill.getBillName())
                .balance(secondUserSecondBill.getBalance())
                .build();

        List<BillDtoResponse> billDtoResponseList = new ArrayList<>();
        billDtoResponseList.add(billDtoResponseFirst);
        billDtoResponseList.add(billDtoResponseSecond);

        BillResponse standardBillResponse = BillResponse.builder()
                .message("Success")
                .login(secondUser.getLogin())
                .billList(billDtoResponseList)
                .build();
        when(userRepository.findByLogin(secondUser.getLogin())).thenReturn(Optional.of(secondUser));
        when(billRepository.findAll()).thenReturn(fullBillList);
        when(billConverter.convert(secondUserFirstBill)).thenReturn(billDtoResponseFirst);
        when(billConverter.convert(secondUserSecondBill)).thenReturn(billDtoResponseSecond);

        BillResponse billResponseTest = subj.findBillsByUser(billRequest);
        assertEquals(standardBillResponse, billResponseTest);
    }
}