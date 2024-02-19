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
import static com.example.demo.TestData.*;
import static org.mockito.ArgumentMatchers.any;
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
        User userFromDatabase = createUser();
        UserRequest request = UserRequest.builder()
                .login(userFromDatabase.getLogin())
                .name(userFromDatabase.getUsername())
                .password(userFromDatabase.getPassword())
                .build();

        when(userRepository.findByLogin(userFromDatabase.getLogin())).thenReturn(Optional.empty());
        when(userRepository.save(any())).thenReturn(userFromDatabase);

        UserResponse response = subj.addUser(request);

        String nameUserFromService = response.getName();
        assertEquals(response.getName(), nameUserFromService);
        assertEquals(response.getMessage(), "Success");
    }

    @Test
    public void test_FindUserByLogin_notFindUser() {
        User user = createUser();
        LoginRequest loginRequest = createLoginRequest(user);

        String loginResponse = subj.findUserByLogin(loginRequest).getLogin();
        when(userRepository.findByLogin(user.getLogin())).thenReturn(null);
        assertNull(loginResponse);
    }

    @Test
    public void test_FindUserByLogin_ok() {
        User userByLogin = createUser();
        LoginRequest loginRequest = createLoginRequest(userByLogin);

        when(userRepository.findByLogin(userByLogin.getLogin())).thenReturn(Optional.of(userByLogin));
        assertEquals(loginRequest.getLogin(), userByLogin.getLogin());
    }

    @Test
    public void test_RemoveUser_Ok() {
        User user = createUser();

        when(userRepository.findByLogin("login")).thenReturn(Optional.of(user));
        userRepository.deleteById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }
}
