package com.example.demo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    private final ObjectMapper objectMapper; // создаем объект для возможности конвертации в формат JSON

    public MainServlet() {
        this.objectMapper = new ObjectMapper();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(ServletConfiguration.class);
        String uri = req.getRequestURI();// запрос uri (у меня /login)
        Controller controller = context.getBean(uri, Controller.class); //создаем бин указанного uri (LoginController)
        try {
            Object request = objectMapper.readValue(req.getInputStream(), controller.getRequestClass());// получаем запрос
            Object response = controller.execute(request);// обрабатывается запрос
            resp.setContentType("application/json");//приведение к типу JSON
            objectMapper.writeValue(resp.getOutputStream(), response);// выводится ответ
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
