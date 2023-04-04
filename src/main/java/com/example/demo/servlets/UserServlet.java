package com.example.demo.servlets;

import com.example.demo.model.Bill;
import com.example.demo.service.ServiceConfiguration;
import com.example.demo.service.impl.BillService;
import com.example.demo.service.impl.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Service
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
        BillService billService = context.getBean(BillService.class);
        UserService userService = context.getBean(UserService.class);

        PrintWriter writer = response.getWriter();
        String login = request.getParameter("login"); // задала переменную, ее прога должна считать в URL
        userService.findUserByLogin(login); // проверяет есть такой логин или нет

        Integer idUserByLogin = userService.findUserByLogin(login).getId(); // по логину ищет ID

        List<Bill> billList = billService.findBillsByUser(userService.findUserById(idUserByLogin));// находит счета по пользователю

        if (login != null) { // открывается сессия
            HttpSession sessionUserId = request.getSession();
            sessionUserId.setAttribute("userId", idUserByLogin); // из URL берет логин и по нему ID
            HttpSession sessionBills = request.getSession();
            sessionBills.setAttribute("userBills", billList); // берет счета
            writer.println(billList); // печатает счета
        } else {
            writer.print("You need to registration");
        }
    }
}
