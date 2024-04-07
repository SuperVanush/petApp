package com.example.demo.controller;

import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add-user")
    public RegistrationUserResponse addUser(@RequestBody RegistrationUserRequest request) {
        return userService.addUser(request);
    }

    @PostMapping("/delete-user")
    public LoginResponse deleteUser(@RequestBody LoginRequest request) {
        return userService.deleteUser(request);
    }
}
