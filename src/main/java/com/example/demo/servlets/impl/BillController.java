package com.example.demo.servlets.impl;

import com.example.demo.servlets.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BillController implements Controller {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

    }

    @Override
    public Object execute(Object request) {
        return null;
    }

    @Override
    public Class getRequestClass() {
        return null;
    }
}