package com.example.demo.servlets.impl;

import com.example.demo.model.dto.Request.UserRequest;
import com.example.demo.model.dto.Response.UserResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/user")
@RequiredArgsConstructor
public class UserController implements Controller<UserRequest, UserResponse> {
    private final UserService userService;

    @Override
    public UserResponse execute(UserRequest request) {
        String name = request.getName();
        String login = request.getLogin();
        String password = request.getPassword();
        userService.addUser(name, login, password);
        String  loginAddedUser = userService.findUserByLogin(login).getLogin();
        return new UserResponse("User with login  " +loginAddedUser + "added");
    }


    @Override
    public Class<UserRequest> getRequestClass() {
        return UserRequest.class;
    }
}