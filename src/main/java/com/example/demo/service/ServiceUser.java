package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;

public interface ServiceUser {

    RegistrationUserResponse addUser(RegistrationUserRequest request);

    User authorizationUser(LoginRequest request);
}
