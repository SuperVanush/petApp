package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {
    @Autowired
    UserService subj;
    @MockBean
    UserRepository userRepository;

    @MockBean
    BillService billService;


    @Before
    public void setUp() {

    }

    @Test
    public void test_AddUser() {
        User user = User.builder()
                .username("qqq")
                .login("qqq")
                .password("qqq")
                .build();
        String userName = "qqq";

        User userFromDatabase = User.builder()
                .login("qqq")
                .username("qqq")
                .password("qqq")
                .build();

        when(userRepository.save(user)).thenReturn(userFromDatabase);
        UserRequest request = UserRequest.builder()
                .login("qqq")
                .name("qqq")
                .password("qqq")
                .build();
        UserResponse response = subj.addUser(request);
        String nameUserFromService = response.getName();
        assertEquals(userName, nameUserFromService);
    }

    @Test
    public void test_FindUserByLogin_notFindUser() {
        LoginRequest loginRequest = LoginRequest.builder()
                .login("rrr")
                .build();
        String loginResponse = subj.findUserByLogin(loginRequest).getLogin();
        when(userRepository.findByLogin("rrr")).thenReturn(null);
        assertNull(loginResponse);
    }

    @Test
    public void test_FindUserByLogin_ok() {
        LoginRequest loginRequest = new LoginRequest("qqq", "qqq");
        User userByLogin = User.builder()
                .id(1)
                .login("qqq")
                .build();
        when(userRepository.findByLogin("qqq")).thenReturn(Optional.of(userByLogin));
        assertEquals(loginRequest.getLogin(), userByLogin.getLogin());
    }

    @Test
    public void test_RemoveUser_Ok() {
        User user = User.builder()
                .login("ddd")
                .id(1)
                .build();
        when(userRepository.findByLogin("ddd")).thenReturn(Optional.of(user));
        userRepository.deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}
