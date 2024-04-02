package com.example.demo.controller;

import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add-user")
    public UserResponse addUser(@RequestBody UserRequest request) {
        return userService.addUser(request);
    }

    @GetMapping("/login")
    public LoginResponse findUser(@RequestBody LoginRequest request) {
        return userService.findUserByLogin(request);
    }

    @PostMapping("/remove-user")
    public LoginResponse removeUser(@RequestBody LoginRequest request) {
        return userService.removeUser(request);
    }
}