package com.example.demo.servlets.impl;

import com.example.demo.model.dto.request.UserRequest;
import com.example.demo.model.dto.response.UserResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/remove-user")
@RequiredArgsConstructor
public class RemoveUserController implements Controller<UserRequest, UserResponse> {

    private final UserService userService;

    @Override
    public UserResponse execute(UserRequest request) {
        String login = request.getLogin();
        userService.removeUser(login);
        return new UserResponse("User with login  " + login + "  was removed");
    }

    @Override
    public Class<UserRequest> getRequestClass() {
        return UserRequest.class;
    }
}
