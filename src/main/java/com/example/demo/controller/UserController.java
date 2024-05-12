package com.example.demo.controller;

import com.example.demo.model.dto.request.RegistrationUserRequest;
import com.example.demo.model.dto.response.RegistrationUserResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.service.ServiceUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final ServiceUser serviceUser;

    @PostMapping("/add-user")
    public RegistrationUserResponse addUser(@RequestBody RegistrationUserRequest request) {
        return serviceUser.addUser(request);
    }

    @GetMapping("/authorization_user/{login}")
    public UserResponse authorizationUser(@PathVariable String login) {
        return serviceUser.authorizationUser(login);
    }

    @PostMapping("/delete-user")
    public void deleteUser(@RequestBody UUID userId) {
        serviceUser.deleteUser(userId);
    }
}
