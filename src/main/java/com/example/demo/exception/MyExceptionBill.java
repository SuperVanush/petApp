package com.example.demo.exception;

public class MyExceptionBill extends RuntimeException {

    public MyExceptionBill(String message) {
        super(message);
    }

    public MyExceptionBill() {
    }
}
