package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionUser;
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

    private final UserService userService;

    private final String SUCCESS_MESSAGE = "Success";
    private final String ERROR_MESSAGE = "Enter correct Login or Registration";

    @Override
    public LoginResponse execute(LoginRequest request) {
        String login = request.getLogin();
        try {
            User findUser = userService.findUserByLogin(login);
            String findUserName = findUser.getUsername();
            return getLoginResponse(SUCCESS_MESSAGE, findUserName);
        } catch (MyExceptionUser exceptionUser) {
            return getLoginResponse(ERROR_MESSAGE, exceptionUser.getMessage());
        }
    }

    private LoginResponse getLoginResponse(String message, String userName) {
        return LoginResponse.builder()
                .message(message)
                .userName(userName)
                .build();
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}



