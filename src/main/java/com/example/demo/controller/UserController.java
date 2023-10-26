package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final UserService userService;

    @PostMapping("/add-user")
    public UserResponse addUser(@RequestBody UserRequest request) {
        Iterable<User> user = userRepository.findAll();
        String name = request.getName();
        String login = request.getLogin();
        String password = request.getPassword();
        userService.addUser(name, login, password);

        return new UserResponse("Success");
    }

    public Class<UserRequest> getRequestClass() {
        return UserRequest.class;
    }
}