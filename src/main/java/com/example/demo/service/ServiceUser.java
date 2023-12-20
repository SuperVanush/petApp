package com.example.demo.service;

import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.UserResponse;

public interface ServiceUser {

    UserResponse addUser(UserRequest request);

    LoginResponse findUserByLogin(LoginRequest request);
}