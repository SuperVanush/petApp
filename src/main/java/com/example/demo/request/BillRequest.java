package com.example.demo.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BillRequest {
    private String billname;
    private String username;
    private BigDecimal balance;
}
