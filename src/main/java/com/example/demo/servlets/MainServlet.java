package com.example.demo.servlets;

import com.example.demo.servlets.impl.LoginController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServletConfiguration.class);
        LoginController loginController = context.getBean(LoginController.class);
        loginController.doPost(req,resp);
    }
}
