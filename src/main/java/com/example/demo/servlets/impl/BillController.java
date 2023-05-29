package com.example.demo.servlets.impl;

import com.example.demo.servlets.Controller;
import org.springframework.stereotype.Service;
@Service ("/bill")
public class BillController implements Controller {

    @Override
    public Object execute(Object request) {
        return null;
    }

    @Override
    public Class getRequestClass() {
        return null;
    }
}