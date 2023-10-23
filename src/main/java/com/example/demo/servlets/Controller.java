package com.example.demo.servlets;

import org.springframework.web.bind.annotation.RestController;

@RestController
public interface Controller<REQ, RES> {

    RES execute(REQ request);

    Class<REQ> getRequestClass();
}
