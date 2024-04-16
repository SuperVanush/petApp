package com.example.demo.service;

import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.model.dto.response.UserResponse;

public interface ServiceUser {

    RegistrationUserResponse addUser(RegistrationUserRequest request);

    UserResponse authorizationUser(String login);
}
