package com.example.demo.service.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.BillRequest;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.BillDtoResponse;
import com.example.demo.model.dto.response.BillResponse;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends TestCase {

    UserService subj;
    UserRepository userRepository;
    BillService billService;

    @Before
    public void setUp() {
        userRepository = mock(UserRepository.class);
        subj = new UserService(userRepository);
    }

    @Test
    public void test_AddUser() {
        User user = User.builder().username("qqq").login("qqq").password("qqq").build();
        String userName = "qqq";

        User userFromDatabase = User.builder().login("qqq").username("qqq").password("qqq").build();

        when(userRepository.save(user)).thenReturn(userFromDatabase);
        UserRequest request = UserRequest.builder().login("qqq").name("qqq").password("qqq").build();
        UserResponse response = subj.addUser(request);
        String nameUserFromService = response.getName();
        assertEquals(userName, nameUserFromService);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_FindUserByLogin_notFindUser() {
        LoginRequest loginRequest = LoginRequest.builder().login("rrr").build();
        LoginResponse loginResponse = subj.findUserByLogin(loginRequest);
        when(userRepository.findByLogin("rrr")).thenReturn(null);
        assertNull(loginResponse);
    }

    @Test
    public void test_FindUserByLogin_ok() {
        User userByLogin = User.builder().id(1).login("qqq").build();
        BillRequest billRequest = new BillRequest("TestBill1", "qqq", BigDecimal.valueOf(555));


        BillDtoResponse billDtoResponse = BillDtoResponse.builder().billName("TestBillName1").build();
        List<BillDtoResponse> bills = new ArrayList<>();
        bills.add(billDtoResponse);

        BillResponse billResponse = new BillResponse("Test 1", "qqq", bills);

        when(userRepository.findByLogin("qqq")).thenReturn(Optional.of(userByLogin));

        when(billService.findBillsByUser(billRequest)).thenReturn(billResponse);
        //      User userFromService = subj.findUserByLogin("qqq");
        //      assertEquals(userByLogin, userFromService);
    }

    @Test(expected = UserNotFoundException.class)
    public void test_RemoveUser_Ok() {
        User user = User.builder().login("ddd").id(1).build();

        //     when(subj.findUserByLogin("ddd")).thenReturn(user);
        userRepository.deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}