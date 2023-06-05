package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.User;
import com.example.demo.model.dto.request.LoginRequest;
import com.example.demo.model.dto.response.LoginResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/login")
@AllArgsConstructor
public class LoginController implements Controller<LoginRequest, LoginResponse> {

    private final UserService userService;

    @Override
    public LoginResponse execute(LoginRequest request) {
        String login = request.getLogin();
        try {
            User findUser = userService.findUserByLogin(login);
            return new LoginResponse("Hello      " + findUser.getUsername());
        } catch (MyExceptionUser exceptionUser) {
            return new LoginResponse("Enter correct Login or Registration");
        }
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}



