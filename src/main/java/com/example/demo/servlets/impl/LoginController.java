package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.User;
import com.example.demo.model.dto.Request.LoginRequest;
import com.example.demo.model.dto.Response.LoginResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class LoginController implements Controller<LoginRequest, LoginResponse> {
    private UserService userService;
    @Override
    public LoginResponse execute(LoginRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        String name = request.getUsername();
        try {
            User findUser = userService.findUserByLogin(login);
            return new LoginResponse("Hello      " + findUser.getName());
        } catch (MyExceptionUser exceptionUser) {
            User addUser = userService.addUser(name, login, password);
            return new LoginResponse("Your ID  " + addUser.getId());
        }
    }


    @Override
    public Class<LoginRequest> getRequestClass() {
        return null;
    }
}



