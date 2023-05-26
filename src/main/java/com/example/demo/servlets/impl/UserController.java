package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.User;
import com.example.demo.model.dto.Request.LoginRequest;
import com.example.demo.model.dto.Response.LoginResponse;
import com.example.demo.service.impl.UserService;
import com.example.demo.servlets.Controller;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service("/user") //сделала еще один адреc
@AllArgsConstructor
public class UserController implements Controller<LoginRequest, LoginResponse> {
    //когда имплементировала, сделала Дженериками LoginRequest, LoginResponse,
    // потому, что мне нужны те же поля, что и в LoginController
    private final UserService userService;

    @Override
    public LoginResponse execute(LoginRequest request) {
        String name = request.getName();
        String login = request.getLogin();
        String password = request.getPassword();
        try {
            User findUser = userService.findUserByLogin(login);
            return new LoginResponse("User with login " + findUser.getLogin() + " already exist");
        } catch (MyExceptionUser exceptionUser) {
            userService.addUser(name, login, password);
            String loginAddedUser = userService.findUserByLogin(login).getLogin();
            return new LoginResponse("User with login  " + loginAddedUser + "  added");
        }
    }

    @Override
    public Class<LoginRequest> getRequestClass() {
        return LoginRequest.class;
    }
}