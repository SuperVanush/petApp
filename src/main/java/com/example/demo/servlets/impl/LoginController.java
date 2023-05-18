package com.example.demo.servlets.impl;

import com.example.demo.exception.MyExceptionUser;
import com.example.demo.model.User;
import com.example.demo.service.impl.UserService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
public class LoginController extends HttpServlet {
    private BillController billController;
    private UserService userService;

    public LoginController(BillController billController, UserService userService) {
        this.billController = billController;
        this.userService = userService;
    }

    //метод doPost потому, что этот метод применяется для изменения данных,
    // а doGet только для получения данных
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, MyExceptionUser {
        PrintWriter writer = resp.getWriter();
        String login = req.getParameter("login"); // берет параметры пользователя
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        try {
            User findUser = userService.findUserByLogin(login); // проверяет по логину
            writer.print("Hello      " + findUser.getName());  // если находит - приветствует
        } catch (MyExceptionUser exceptionUser) {
            User addUser = userService.addUser(name, login, password);// если не находит, добавляет
            writer.print("Your ID  " + addUser.getId()); // берет ID нового пользователя и выводит его
        }
    }
}



