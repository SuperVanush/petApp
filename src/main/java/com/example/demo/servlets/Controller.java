package com.example.demo.servlets;

public interface Controller <REQ,RES> {
    RES execute (REQ request);

    Class <REQ> getRequestClass();
}
