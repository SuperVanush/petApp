package com.example.demo.exception;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String USER_NOT_FOUND = "USER NOT FOUND";
    private static final String ERROR_USER = "THIS USER EXIST";
    private static final String BILL_NOT_FOUND = "BILL NOT FOUND";
    private static final String ERROR_BALANCE = "ERROR BALANCE";

    @ResponseBody
    @ExceptionHandler (UserException.class)
    protected ResponseEntity<Object> userException (UserException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(USER_NOT_FOUND, e.getMessage());
    return buildErrorResponse(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler (BillException.class)
    protected ResponseEntity<Object> billException (BillException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(BILL_NOT_FOUND, e.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler (BalanceException.class)
    protected ResponseEntity<Object> balanceException (BalanceException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_BALANCE, e.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler (RegistrationException.class)
    protected ResponseEntity<Object> balanceException (RegistrationException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(ERROR_USER, e.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @NonNull
    private ResponseEntity<Object> buildErrorResponse(ExceptionResponse exceptionResponse, HttpStatus status) {
        log.error(exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
