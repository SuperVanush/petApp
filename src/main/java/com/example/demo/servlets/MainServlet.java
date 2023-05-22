package com.example.demo.servlets;

import com.example.demo.servlets.impl.LoginController;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {
    private final Map<String, Controller> controllers;
    private final ObjectMapper objectMapper;

    public MainServlet() {
        this.controllers = new HashMap<String, Controller>();
        this.controllers.put("/login", new LoginController());
        this.objectMapper = new ObjectMapper();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        Controller controller = controllers.get(uri);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
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
