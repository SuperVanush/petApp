package com.example.demo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       ApplicationContext context = new AnnotationConfigApplicationContext(ServletConfiguration.class);
        String uri = req.getRequestURI();
        Controller<Object, Object> controller = context.getBean(uri, Controller.class);
        try {
            Object request = objectMapper.readValue(req.getInputStream(), controller.getRequestClass());
            Object response = controller.execute(request);
            resp.setContentType("application/json");
            objectMapper.writeValue(resp.getOutputStream(), response);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write(e.getMessage());
        }
    }
}
