package com.example.demo.servlets;

import com.example.demo.request.LoginRequest;
import com.example.demo.response.LoginResponse;

public class LoginController implements
        Controller<LoginRequest, LoginResponse> {
    @Override
    public LoginResponse execute(LoginRequest request) {
        if ("MMM".equals(request.getUsername())){
            return new LoginResponse(true);
        }
        return null;
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return null;
    }
}
