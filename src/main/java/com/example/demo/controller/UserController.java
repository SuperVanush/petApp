package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
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
        try {
            String name = request.getName();
            String login = request.getLogin();
            String password = request.getPassword();
            User addUser = userService.addUser(name, login, password);
            return getSuccessUserResponse(addUser);

        } catch (UserNotFoundException e) {
            return getErrorUserResponse(e.getMessage());
        }
    }

    @GetMapping("/login")
    public LoginResponse findUser(@RequestBody LoginRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());
            return getSuccessLoginResponse(userByLogin);

        } catch (UserNotFoundException e) {
            return getErrorLoginResponse(e.getMessage());
        }
    }

    @PostMapping("/remove-user")
    public LoginResponse removeUser(@RequestBody UserRequest request) {
        try {
            String login = request.getLogin();
            userService.removeUser(login);

            return getSuccessUserRemoveResponse(login);

        } catch (UserNotFoundException e) {
            return getErrorUserRemoveResponse(e.getMessage());
        }
    }


    private UserResponse getSuccessUserResponse(User user) {
        return UserResponse.builder()
                .message("Success")
                .name(user.getUsername())
                .build();
    }

    private UserResponse getErrorUserResponse(String message) {
        return UserResponse.builder()
                .message(message)
                .build();
    }

    private LoginResponse getSuccessLoginResponse(User user) {
        return LoginResponse.builder()
                .message("Hello")
                .login(user.getLogin())
                .build();
    }

    private LoginResponse getErrorLoginResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }

    private LoginResponse getSuccessUserRemoveResponse(String login) {
        return LoginResponse.builder()
                .message("Success  remove")
                .login(login)
                .build();
    }

    private LoginResponse getErrorUserRemoveResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }
}