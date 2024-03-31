package com.example.demo.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionResponse {
    private String status;
    private String message;
}
