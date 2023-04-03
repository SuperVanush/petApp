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
import java.util.List;

@Service
public class LoginServlet extends HttpServlet {

    ApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
    BillService billService = context.getBean(BillService.class);
    UserService userService = context.getBean(UserService.class);

    public LoginServlet() {
    }

    public LoginServlet(BillService billService, UserService userService) {
        this.userService = userService;
        this.billService = billService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String userLogin = request.getParameter("login");
        Integer idUserByLogin = userService.findUserByLogin("MMM").getId();
        String userPassword = request.getParameter("password");
        List<Bill> billList = billService.findBillsByUser(userService.findUserById(idUserByLogin));
        if ("MMM".equals(userLogin) && "user1".equals(userPassword)) {
            HttpSession sessionUserId = request.getSession();
            sessionUserId.setAttribute("userId", idUserByLogin);
            HttpSession sessionBills = request.getSession();
            sessionBills.setAttribute("userBills", billList);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
