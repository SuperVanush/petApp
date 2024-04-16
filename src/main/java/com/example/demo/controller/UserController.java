package com.example.demo.controller;

import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.model.dto.response.UserDeleteResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add-user")
    public RegistrationUserResponse addUser(@RequestBody RegistrationUserRequest request) {
        return userService.addUser(request);
    }

    @GetMapping("/authorization_user/{login}")
    public UserResponse authorizationUser(@PathVariable String login) {
        return userService.authorizationUser(login);
    }

    @PostMapping("/delete-user")
    public UserDeleteResponse deleteUser(@RequestBody UUID userId) {
        return userService.deleteUser(userId);
    }
}
