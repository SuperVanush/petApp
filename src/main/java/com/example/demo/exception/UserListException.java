package com.example.demo.exception;

public class UserListException extends Exception{


    public UserListException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
