package com.example.demo.exception;

public class MyExceptionTransfer extends RuntimeException {

    public MyExceptionTransfer(String message) {
        super(message);
    }

    public MyExceptionTransfer() {
    }
}
