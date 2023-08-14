package com.example.demo.servlets.impl;

import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/login")
@RequiredArgsConstructor
public class LoginController implements Controller<LoginRequest, LoginResponse> {

    private static final String SUCCESS_MESSAGE = "Hello";


    private final UserService userService;

    @Override
    public LoginResponse execute(LoginRequest request) {
        try {
            User userByLogin = userService.findUserByLogin(request.getLogin());
            return getSuccessResponse(userByLogin);

        } catch (UserNotFoundException e) {
            return getErrorResponse(e.getMessage());
        }
    }

    private LoginResponse getSuccessResponse(User user) {
        return LoginResponse.builder()
                .message(SUCCESS_MESSAGE)
                .login(user.getLogin())
                .build();
    }

    private LoginResponse getErrorResponse(String message) {
        return LoginResponse.builder()
                .message(message)
                .build();
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}
