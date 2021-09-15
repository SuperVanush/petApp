package com.example.demo;


import com.example.demo.service.UserService;

public class Main {

    public static void main(String[] args) {
        UserService startUserService = new UserService();
        startUserService.startApp();
    }
}
