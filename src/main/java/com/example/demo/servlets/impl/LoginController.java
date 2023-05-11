package com.example.demo.servlets.impl;

import com.example.demo.model.request.LoginRequest;
import com.example.demo.servlets.response.LoginResponse;
import com.example.demo.servlets.Controller;

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
