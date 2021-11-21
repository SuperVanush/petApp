package com.example.demo;

import com.example.demo.exception.UserListException;
import com.example.demo.view.StartProgram;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws  UserListException {
        StartProgram startProgram = new StartProgram();
        startProgram.startApp();
    }
}
