package com.example.demo.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter writer = response.getWriter(); //сделала первое меню,
        writer.println("MENU");
        writer.println("1. Registration");
        writer.println("2. Entrance to the cabinet"); // дальше для того,
                                                      // что бы понять правильно или нет, решила сначала вход в кабинет проверить
        writer.println("3. Remove user");
        writer.println("0. EXIT");
        HttpSession sessionMenuNumber = request.getSession(); // назначила пункты меню
        sessionMenuNumber.setAttribute("menuNumber", 1);
        sessionMenuNumber.setAttribute("menuNumber", 2);
        sessionMenuNumber.setAttribute("menuNumber", 3);
        Integer numberOfChoice = (Integer) request.getSession().getAttribute("menuNumber");
        if (numberOfChoice == 2) {                      // считывает в URL пункт, который мне надо
                                                        // и кидает дальше на др. страничку
            response.sendRedirect("/user");

        }

    }
}
