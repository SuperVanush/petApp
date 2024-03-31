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

    private static final String USER_NOT_FOUND = "User not found";
    private static final String BILL_NOT_FOUND = "Bill not found";
    private static final String TRANSFER_NOT_FOUND = "Transfer not found";

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> UserNotFoundException(UserNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(USER_NOT_FOUND, ex.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(BillNotFoundException.class)
    protected ResponseEntity<Object> BillNotFoundException(BillNotFoundException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(BILL_NOT_FOUND, ex.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(TransferException.class)
    protected ResponseEntity<Object> TransferException(TransferException ex) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(TRANSFER_NOT_FOUND, ex.getMessage());
        return buildErrorResponse(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @NonNull
    private ResponseEntity<Object> buildErrorResponse(ExceptionResponse exceptionResponse, HttpStatus status) {
        log.error(exceptionResponse.getMessage());
        return new ResponseEntity<>(exceptionResponse, status);
    }
}
