package com.example.demo.controller;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    @PostMapping( "/add-user")
    public UserResponse addUser(@RequestBody UserRequest request) {
        userRepository.findAll();
        String name = request.getName();
        String login = request.getLogin();
        String password = request.getPassword();
        userService.addUser(name, login, password);

        return new UserResponse("Success");
    }

    @GetMapping("/login")
    public LoginResponse addUser(@RequestBody LoginRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());
            return getSuccessResponse(userByLogin);

        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private LoginResponse getSuccessResponse(User user) {
        return LoginResponse.builder()
                .message("Hello")
                .login(user.getLogin())
                .build();
    }

    private LoginResponse getErrorResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }
}