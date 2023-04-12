package com.example.demo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainServlet extends HttpServlet {

    private Map<String, Controller> controllers;
    private ObjectMapper objectMapper;

    public MainServlet() {
        this.controllers = new HashMap<String, Controller>();
        this.controllers.put("/login", new LoginController());

        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        Controller controller = controllers.get(uri);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            Object req = objectMapper.readValue(request.getInputStream(), controller.getRequestClass());
            Object resp = controller.execute(req);
            objectMapper.writeValue(response.getOutputStream(), resp);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
    }
}
