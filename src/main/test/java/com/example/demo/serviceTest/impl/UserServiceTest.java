package com.example.demo.serviceTest.impl;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;
import com.example.demo.dto.request.RegistrationUserRequest;
import com.example.demo.dto.response.RegistrationUserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.converter.Converter;
import com.example.demo.service.impl.UserService;
import com.example.demo.serviceTest.TestData;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)

public class UserServiceTest extends TestCase {

    @Autowired
    UserService subj;

    @MockBean
    UserRepository userRepository;
    @MockBean
    Converter<Object, Object> converter;

    @Before
    public void setUp() {
    }

    @Test
    public void addUser_OK() {
        User user = TestData.createUser();

        RegistrationUserRequest registrationUserRequest = new RegistrationUserRequest();
        registrationUserRequest.setLogin(user.getLogin());

        String message = "Success";

        RegistrationUserResponse registrationUserResponse = new RegistrationUserResponse();
        registrationUserResponse.setMessage(message);
        registrationUserResponse.setUserName(user.getUserName());

        when(userRepository.save(any())).thenReturn(user);
        RegistrationUserResponse testResponse = subj.addUser(registrationUserRequest);
        assertEquals(testResponse.getMessage(), registrationUserResponse.getMessage());
        assertEquals(testResponse.getUserName(), registrationUserResponse.getUserName());
    }

    @Test
    public void authorizationUser_Ok() {
        User user = TestData.createUser();

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getId());

        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        UserResponse userResponseReturn = subj.authorizationUser(user.getLogin());
        assertEquals(userResponse.getUserId(), userResponseReturn.getUserId());
    }

    @Test
    public void deleteUser_OK() {
        User user = TestData.createUser();

        doNothing().when(userRepository).deleteById(user.getId());
    }

    @Test
    public void findUserByLogin_ok() {
        User user = TestData.createUser();

        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        User returnUser = subj.findUserByLogin(user.getLogin());

        assertEquals(user, returnUser);
    }

    @Test
    public void findUserById_ok() {
        User user = TestData.createUser();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User returnUser = subj.findUserById(user.getId());

        assertEquals(user, returnUser);
    }

}
