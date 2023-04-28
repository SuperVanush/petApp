package com.example.demo.servlets;

import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;

public class LoginController implements
        Controller<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse execute(LoginRequest request) {

        if ("Anna".equals(request.getUsername())) {
            return new LoginResponse(true);
        }
        return new LoginResponse(false);
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}
