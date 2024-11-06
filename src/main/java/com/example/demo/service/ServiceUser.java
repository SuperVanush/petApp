package com.example.demo.service;

import com.example.demo.dto.request.RegistrationUserRequest;
import com.example.demo.dto.response.RegistrationUserResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.model.User;

import java.util.UUID;

public interface ServiceUser {

    RegistrationUserResponse addUser(RegistrationUserRequest request);

    UserResponse authorizationUser(String login);

    User findUserById(UUID userId);

    void deleteUser(UUID userId);

    User findUserByLogin(String login);
}
